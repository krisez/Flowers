package cn.krisez.flowers.presenter.person_presenter;

import cn.krisez.flowers.model.person_model.IPersonModel;
import cn.krisez.flowers.model.person_model.PersonModel;
import cn.krisez.flowers.ui.main_ui.IPersonView;

/**
 * Created by Krisez on 2017-11-02.
 */

public class PersonPresenter implements PListener{
    private IPersonModel model;
    private IPersonView view;

    public PersonPresenter(IPersonView view) {
        this.view = view;
        model = new PersonModel();
    }

    public void upload(String uri) {
        model.uploadFile(uri,view.getUserName(),this);
    }

    public void changeInfo(String name, String sex) {
        model.changeInfo(view.getUserName(),name,sex,this);
    }

    @Override
    public void successful() {
        //图片上传保证切换fragment的时候头像不会消失

    }

    @Override
    public void failed() {
        view.showError("貌似出现了一些错误！");
    }


}
