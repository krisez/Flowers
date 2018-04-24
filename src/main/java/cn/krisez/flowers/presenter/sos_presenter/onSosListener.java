package cn.krisez.flowers.presenter.sos_presenter;

import java.util.List;

import cn.krisez.flowers.entity.Apply;

/**
 * Created by Krisez on 2018-03-18.
 */

public interface onSosListener {
    void success(List<Apply> applyList);
    void goOk();
    void failed(String s);
}
