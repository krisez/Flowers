package cn.krisez.flowers.entity;

import android.widget.ImageView;

/**
 * Created by Krisez on 2017/4/18.
 */

public class ImgBean {

    //  id
    private int index;
    // 图片 url
    private String imageUrl;
    //图片
    private ImageView imageView;

    public ImgBean(int index, String imageUrl, ImageView imageView) {
        this.index = index;
        this.imageUrl = imageUrl;
        this.imageView = imageView;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
