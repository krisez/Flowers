package cn.krisez.flowers.im.model;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.krisez.flowers.App;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.im.presenter.OnSearchListener;

/**
 * Created by Krisez on 2018-03-24.
 */

public class SearchModel implements ISearchModel {

    @Override
    public void search(String username, final OnSearchListener listener) {
        BmobQuery<User> query = new BmobQuery<>();
        if(TextUtils.equals(username, App.getUser().getUsername())){
            listener.failed("输入错误");
            return;
        }
        query.addWhereEqualTo("username", username);
        query.setLimit(20);
        query.order("-createdAt");
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    listener.successSearch(list);
                } else {
                    Logger.e(e.getMessage());
                    listener.failed(e.getMessage());
                }
            }
        });

    }
}
