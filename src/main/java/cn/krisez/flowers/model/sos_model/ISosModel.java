package cn.krisez.flowers.model.sos_model;

import cn.krisez.flowers.presenter.sos_presenter.onSosListener;

/**
 * Created by Krisez on 2018-03-18.
 */

public interface ISosModel {
    void getDatas(String objectId, onSosListener listener);
    void decideGo(String apply_id,String where, onSosListener listener);

}
