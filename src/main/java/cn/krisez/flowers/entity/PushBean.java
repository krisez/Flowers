package cn.krisez.flowers.entity;

/**
 * Created by Krisez on 2018-03-18.
 */

public class PushBean {
    private String alert;

    public PushBean(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }
}
