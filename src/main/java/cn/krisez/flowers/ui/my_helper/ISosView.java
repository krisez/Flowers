package cn.krisez.flowers.ui.my_helper;

import java.util.List;

import cn.krisez.flowers.entity.Apply;

/**
 * Created by Krisez on 2018-03-18.
 */

public interface ISosView {
    String getAuthorId();
    void setList(List<Apply> applies);
    void cancel();
}
