package cn.krisez.flowers.ui.main_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.krisez.flowers.App;
import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.ImagePaperAdapter;
import cn.krisez.flowers.entity.ImgBean;
import cn.krisez.flowers.ui.ask.SendM2SActivity;
import cn.krisez.flowers.ui.helper_ui.HelperActivity;
import cn.krisez.flowers.utils.DensityUtil;


/**
 * Created by Krisez on 2017-12-03.
 */

public class HelperFrag extends Fragment {

    private ViewPager viewpager;
    /**
     * 用于存放轮播效果图片
     */
    private List<ImgBean> imgBeen;

    private int currentItem = 0;//当前页面

    boolean isAutoPlay = true;//是否自动轮播

    private ScheduledExecutorService scheduledExecutorService;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.what == 100) {
                viewpager.setCurrentItem(currentItem);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_helper,container,false);
        viewpager = (ViewPager) v.findViewById(R.id.helper_banner);
        imgBeen = new ArrayList<>();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(isAutoPlay){
            startPlay();
        }

        initBanner(null);

        view.findViewById(R.id.helper_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.getUser() != null){
                    Intent intent = new Intent(getContext(), SendM2SActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.helper_put).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.getUser() != null){
                    Intent intent = new Intent(getContext(), HelperActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initBanner(List<ImgBean> imgBeans){
        //imgBeen = imgBeans;
        ImageView imageView = (ImageView) LayoutInflater.from(App.getContext()).inflate(R.layout.banner_view_item, null);
        imageView.setImageBitmap(DensityUtil.readBitMap(getContext(),R.drawable.cq));
        ImageView imageView1 = (ImageView) LayoutInflater.from(App.getContext()).inflate(R.layout.banner_view_item, null);
        imageView1.setImageBitmap(DensityUtil.readBitMap(getContext(),R.drawable.cq));
        imgBeen.add(new ImgBean(1,"http://law.krisez.cn",imageView));
        imgBeen.add(new ImgBean(1,"http://law.krisez.cn",imageView1));
        ImagePaperAdapter adapter = new ImagePaperAdapter((ArrayList) imgBeen);

        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        viewpager.addOnPageChangeListener(new MyPageChangeListener());
    }

    /**
     * 开始轮播图切换
     */
    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
        //根据他的参数说明，第一个参数是执行的任务，第二个参数是第一次执行的间隔，第三个参数是执行任务的周期；
    }

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewpager) {
                currentItem = (currentItem + 1) % imgBeen.size();
                handler.sendEmptyMessage(100);
            }
        }
    }

    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewpager.getCurrentItem() == viewpager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        viewpager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewpager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewpager.setCurrentItem(viewpager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isAutoPlay = false;
        scheduledExecutorService.shutdown();
    }
}
