package cn.krisez.flowers.model.help_model;

import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.presenter.Listener;

/**
 * Created by Krisez on 2018-03-18.
 */

public interface IHelpModel {
    void submit(Apply apply, Listener listener);
}
