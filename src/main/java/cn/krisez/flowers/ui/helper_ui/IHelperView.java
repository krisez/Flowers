package cn.krisez.flowers.ui.helper_ui;

/**
 * Created by Krisez on 2018-03-18.
 */

public interface IHelperView {
    String getName();
    String getType();
    String getDescribe();
    void over();
    void failed(String s);
}
