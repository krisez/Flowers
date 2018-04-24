package cn.krisez.flowers.model.login_model;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.List;
import java.util.UUID;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.krisez.flowers.App;
import cn.krisez.flowers.entity.QQ;
import cn.krisez.flowers.entity.QQInfo;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.presenter.login_presenter.LoginListener;

/**
 * Created by Krisez on 2018-01-29.
 * 登录model
 * 1学生，2老师
 */

public class LoginModel implements ILoginModel {

    @Override
    public void getUser(final String account, String mm, final LoginListener listener) {
        User user = new User();
        user.setUsername(account);
        user.setPassword(mm);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User u, BmobException e) {
                if (e == null) {
                    App.setUser(u);
                    listener.success();
                } else {
                    if (e.getErrorCode() == 101)
                        listener.failed("用户名或密码不正确");
                }
            }
        });

    }

    @Override
    public void register(String account, final String mm, String phone, String sex, final LoginListener listener) {
        User user = new User();
        user.setUsername(account);
        user.setPassword(mm);
        user.setMobilePhoneNumber(phone);
        user.setSex(sex);
        user.setType(0);
        user.setHeadUrl("http://bmob-cdn-17404.b0.upaiyun.com/2018/03/23/08a78bf84093a5a180a7a70bb8d7822f.jpg");
        user.setNickname("取个名字吧~");
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    user.setPassword(mm);
                    laterLogin(user, listener);
                } else {
                    Log.d("asd", "done:" + e.getErrorCode());
                    if (e.getErrorCode() == 202) {
                        listener.failed("用户名已存在");
                    } else
                        listener.failed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void qq(final Fragment fragment, final LoginListener listener) {
        final Tencent mTencent = Tencent.createInstance("1105747214", App.getContext());
        if (!mTencent.isSessionValid()) {
            mTencent.login(fragment, "", new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    final Gson gson = new Gson();
                    final QQ qq = gson.fromJson(o.toString(), QQ.class);
                    QQToken token = new QQToken("1105747214");
                    token.setAccessToken(qq.getAccess_token(), qq.getExpires_in());
                    token.setOpenId(qq.getOpenid());
                    UserInfo info = new UserInfo(App.getContext(), token);
                    info.getUserInfo(new IUiListener() {
                        @Override
                        public void onComplete(Object o) {
                            QQInfo qi = new Gson().fromJson(o.toString(), QQInfo.class);
                            qqLogin(qi, qq.getOpenid(), listener);
                        }

                        @Override
                        public void onError(UiError uiError) {
                            listener.failed(uiError.errorMessage);
                        }

                        @Override
                        public void onCancel() {
                            listener.failed("取消");
                        }
                    });
                }

                @Override
                public void onError(UiError uiError) {
                    listener.failed(uiError.errorMessage);
                }

                @Override
                public void onCancel() {
                    listener.failed("取消");

                }
            });
        }
    }

    private void qqLogin(final QQInfo qi, final String openid, final LoginListener listener) {
        BmobQuery<User> query = new BmobQuery<>();
        final String users = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        query.addWhereEqualTo("mOpenId", openid);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    User user = list.get(0);
                    user.setPassword("000000");
                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                App.setUser(user);
                                listener.success();
                            } else {
                                if (e.getErrorCode() == 101)
                                    listener.failed("用户名或密码不正确");
                            }
                        }
                    });
                } else if (list.size() == 0) {
                    User user = new User();
                    user.setNickname(qi.getNickname());
                    user.setType(0);
                    user.setUsername(users);
                    user.setPassword("000000");
                    user.setSex(qi.getGender());
                    user.setOpenId(openid);
                    user.setHeadUrl(qi.getFigureurl_2());
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User u, BmobException e) {
                            if (e == null) {
                                u.setPassword("000000");
                                laterLogin(u, listener);
                            } else {
                                Log.d("asd", "done:" + e.getErrorCode());
                                if (e.getErrorCode() == 202) {
                                    listener.failed("用户名已存在");
                                } else
                                    listener.failed(e.getMessage());
                            }
                        }
                    });
                } else
                    listener.failed(e.getMessage());
            }
        });
    }

    private void laterLogin(User user, final LoginListener listener) {
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                App.setUser(user);
                Log.d("LoginModel", "done:" + user.toString());
                listener.success();
            }
        });
    }

    /*public void register(final String xh, final String mm, Students students, final LoginListener listener) {
        students.save();
        User user = new User();
        user.setUsername(xh);
        user.setPassword(mm);
        user.setType("1");
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User u, BmobException e) {
                if (e == null) {
                    u.setPassword(mm);
                    SharedPreferenceUtil.setTable(xh, "");
                    laterLogin(u, listener);
                    loginTable(xh,1);
                } else listener.failed(e.getMessage());
            }
        });
    }*/
}
