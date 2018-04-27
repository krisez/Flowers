package cn.krisez.flowers.ui.main_ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.tauth.Tencent;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.krisez.flowers.App;
import cn.krisez.flowers.R;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.presenter.person_presenter.PersonPresenter;
import cn.krisez.flowers.ui.ask.MyAskActivity;
import cn.krisez.flowers.ui.base.ShowFragment;
import cn.krisez.flowers.ui.login_ui.LoginActivity;
import cn.krisez.flowers.ui.my_helper.SosActivity;
import cn.krisez.flowers.ui.my_likes.LikesActivity;
import cn.krisez.flowers.utils.DensityUtil;
import cn.krisez.flowers.widget.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Krisez on 2017/8/8.
 */

public class PersonFrag extends Fragment implements View.OnClickListener, IPersonView {

    public static final int PERSON_AUTHEN = 2;


    private CircleImageView mHead;
    private CircleImageView sex;
    private ImageView mBg;
    private TextView name;
    private TextView id;
    private User mUser;
    private boolean isLogin;

    private TextView shen_name;

    private PersonPresenter personPresenter;

    public static final int REQUEST_PERSON = 1;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 200) {
                resultM();
            }
            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_person, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        personPresenter = new PersonPresenter(this);
        mUser = App.getUser();
        isLogin = mUser != null;

        mHead = (CircleImageView) view.findViewById(R.id.person_head);
        mBg = view.findViewById(R.id.head_bg);
        sex = (CircleImageView) view.findViewById(R.id.person_sex_img);
        name = (TextView) view.findViewById(R.id.person_name);
        id = (TextView) view.findViewById(R.id.person_id);

        shen_name = view.findViewById(R.id.shen_name);
        if (isLogin) {
            resultM();
        }

        view.findViewById(R.id.set_my_ask).setOnClickListener(this);//求助
        view.findViewById(R.id.set_my_post).setOnClickListener(this);//帖子
        view.findViewById(R.id.set_mt_collect).setOnClickListener(this);
        view.findViewById(R.id.set_my_shen).setOnClickListener(this);//身份认证
        view.findViewById(R.id.set_my_set).setOnClickListener(this);//应用设置
        view.findViewById(R.id.set_my_about).setOnClickListener(this);//关于反馈
        view.findViewById(R.id.set_my_rule).setOnClickListener(this);
        view.findViewById(R.id.exit_user).setOnClickListener(this);//退出
        mHead.setOnClickListener(this);//头像点击事件
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.set_my_ask:
                if (isLogin) {
                    intent = new Intent(getContext(), MyAskActivity.class);
                    startActivity(intent);
                } else Toast.makeText(getContext(), "请登录！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.set_my_post:
                if (isLogin) {
                    intent = new Intent(getContext(), SosActivity.class);
                    startActivity(intent);
                } else Toast.makeText(getContext(), "请登录！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.set_mt_collect:
                if (isLogin) {
                    intent = new Intent(getContext(), LikesActivity.class);
                    startActivity(intent);
                } else Toast.makeText(getContext(), "请登录！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.set_my_shen:
                if (isLogin) {
                    intent = new Intent(getContext(), ShowFragment.class);
                    intent.putExtra("class", "authentication");
                    startActivityForResult(intent, PERSON_AUTHEN);
                } else Toast.makeText(getContext(), "请登录！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.set_my_set:
                intent = new Intent(getContext(), ShowFragment.class);
                intent.putExtra("class", "setting");
                startActivityForResult(intent, PERSON_AUTHEN);
                break;
            case R.id.set_my_rule:
                intent = new Intent(getContext(), ShowFragment.class);
                intent.putExtra("class", "rule");
                startActivity(intent);
                break;
            case R.id.set_my_about:
                new AlertDialog.Builder(getContext())
                        .setItems(new String[]{"关于", "反馈"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent1 = new Intent(getContext(), ShowFragment.class);
                                    intent1.putExtra("class", "app");
                                    startActivity(intent1);
                                } else {
                                    new AlertDialog.Builder(getContext())
                                            .setTitle("反馈")
                                            .setMessage("稍等一下~~~\n如需帮助请加QQ:3057981340")
                                            .setPositiveButton("好", null)
                                            .show();
                                }
                            }
                        }).show();
                break;
            case R.id.exit_user:
                if (isLogin) {
                    BmobUser.logOut();
                    isLogin = false;
                    name.setText("点击登陆");
                    id.setText("023");
                    mBg.setImageResource(R.drawable.test);
                    mHead.setImageResource(R.drawable.test);
                    App.setUser(null);
                    shen_name.setText("");
                    BmobIM.getInstance().disConnect();
                    Tencent tencent = Tencent.createInstance("1105747214", getContext());
                    tencent.logout(getContext());
                }
                break;
            case R.id.person_head:
                if (isLogin) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        setHead();
                    }
                } else {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, REQUEST_PERSON);
                }
                break;
        }
    }

    /**
     * 换头像
     */
    private void setHead() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PERSON);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setHead();
                else Toast.makeText(getContext(), "未给权限", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_PERSON:
                if (resultCode == LoginActivity.LOGIN_RESULT) {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(200);
                        }
                    }, 300);
                } else if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            case 0:
                if (resultCode == 0) {
                    name.setText(data.getStringExtra("name"));
                    int res_id = data.getStringExtra("sex").equals("男") ? R.drawable.boy : R.drawable.girl;
                    sex.setImageResource(res_id);
                    personPresenter.changeInfo(data.getStringExtra("name"),
                            data.getStringExtra("sex"));
                }
                break;
            case PERSON_AUTHEN:
                if (resultCode == RESULT_OK)
                    shen_name.setText(data.getStringExtra("authentication"));
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imgPath = uri.getPath();
        displayHead(imgPath);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayHead(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = App.getContext().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayHead(String path) {
        if (path != null) {
            User user = BmobUser.getCurrentUser(User.class);
            String targetPath = Environment.getExternalStorageDirectory() + "/flowers/" + user.getNickname() + "_headPic.jpg";
            //调用压缩图片的方法，返回压缩后的图片path
            final String compressImage = DensityUtil.compressImage(path, targetPath, 30);
            Bitmap bitmap = BitmapFactory.decodeFile(compressImage);
            mHead.setImageBitmap(bitmap);
            mBg.setImageBitmap(bitmap);
            uploadHead(compressImage);
        } else {
            Toast.makeText(getContext(), "获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void resultM() {
        final User user = App.getUser();
        if (!user.getUsername().isEmpty()) {
            isLogin = true;
            final String url = user.getHeadUrl();
            if (!url.isEmpty()) {
                Glide.with(this).load(url).into(mHead);
                Glide.with(this).load(url).into(mBg);
            }
            if (user.getSex().equals("男")) {
                sex.setImageResource(R.drawable.boy);
            } else sex.setImageResource(R.drawable.girl);
            name.setText(user.getNickname());
            id.setText("ID：" + user.getUsername());
            if (user.getType() == 1)
                shen_name.setText("学生");
            MainActivity mainActivity = new MainActivity();
            mainActivity.onActivityResult(0, 160, null);
        }
    }

    @Override
    public String getUserName() {
        return id.getText().toString();
    }

    @Override
    public void uploadHead(String uri) {
        personPresenter.upload(uri);
    }

    @Override
    public void showError(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
}
