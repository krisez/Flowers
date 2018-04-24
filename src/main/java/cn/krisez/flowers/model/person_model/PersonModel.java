package cn.krisez.flowers.model.person_model;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.krisez.flowers.App;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.presenter.person_presenter.PListener;

/**
 * Created by Krisez on 2017-11-02.
 */

public class PersonModel implements IPersonModel{
    @Override
    public void uploadFile(final String uri, String user, final PListener listener) {
        BmobFile file = new BmobFile();
        if(!App.getUser().getHeadUrl().equals("http://bmob-cdn-17404.b0.upaiyun.com/2018/03/23/08a78bf84093a5a180a7a70bb8d7822f.jpg")){
            file.setUrl(App.getUser().getHeadUrl());
            file.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e == null){
                        upPic(uri,listener);
                    }
                }
            });
        }else{
            upPic(uri,listener);
        }
    }

    private void upPic(final String uri , final PListener listener){
        final BmobFile bmobFile = new BmobFile(new File(uri));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    String s = bmobFile.getFileUrl();
                    User u = App.getUser();
                    u.setHeadUrl(s);
                    u.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            listener.successful();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void changeInfo(String id, String nickname, String sex, final PListener listener) {
        User user = App.getUser();
        user.setNickname(nickname);
        user.setSex(sex);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e!=null){
                    listener.failed();
                }
            }
        });
    }
}
