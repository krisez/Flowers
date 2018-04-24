package cn.krisez.flowers.model.person_model;


import cn.krisez.flowers.presenter.person_presenter.PListener;

/**
 * Created by Krisez on 2017-11-02.
 */

public interface IPersonModel {
    void uploadFile(String uri, String user, PListener linsenter);
    void changeInfo(String id, String userName, String sex, PListener listener);
}
