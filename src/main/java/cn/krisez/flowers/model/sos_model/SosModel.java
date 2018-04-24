package cn.krisez.flowers.model.sos_model;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.presenter.sos_presenter.onSosListener;

/**
 * Created by Krisez on 2018-03-18.
 */

public class SosModel implements ISosModel {
    @Override
    public void getDatas(String objectId, final onSosListener listener) {
        BmobQuery<Apply> query = new BmobQuery<>();
        query.addWhereEqualTo("author",objectId);
        query.include("author");
        query.setLimit(20);
        query.findObjects(new FindListener<Apply>() {
            @Override
            public void done(List<Apply> list, BmobException e) {
                if(e == null){
                    listener.success(list);
                }else listener.failed(e.getMessage());
            }
        });
    }

    @Override
    public void decideGo(String apply_id, String where, final onSosListener listener) {
        Apply apply = new Apply();
        apply.setObjectId(apply_id);
        apply.setWhere(where);
        apply.setDeal(true);
        apply.setOver(true);
        apply.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    listener.goOk();
                }else listener.failed(e.getMessage());
            }
        });
    }
}
