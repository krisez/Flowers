package cn.krisez.flowers.presenter.sos_presenter;

import android.widget.Toast;

import java.util.List;

import cn.krisez.flowers.App;
import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.model.sos_model.ISosModel;
import cn.krisez.flowers.model.sos_model.SosModel;
import cn.krisez.flowers.ui.my_helper.ISosView;

/**
 * Created by Krisez on 2018-03-18.
 */

public class SosPresenter implements ISosPresenter ,onSosListener {

    private ISosView mView;
    private ISosModel mModel;

    public SosPresenter(ISosView view) {
        mView = view;
        mModel = new SosModel();
    }

    @Override
    public void getList() {
        mModel.getDatas(mView.getAuthorId(),this);
    }

    @Override
    public void goWhere(String id,String w) {
        mModel.decideGo(id,w,this);
    }

    @Override
    public void success(List<Apply> applyList) {
        mView.setList(applyList);
    }

    @Override
    public void goOk() {
        mView.cancel();
    }

    @Override
    public void failed(String s) {
        Toast.makeText(App.getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
