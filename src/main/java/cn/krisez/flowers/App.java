package cn.krisez.flowers;

import android.app.Application;
import android.content.Context;


import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.im.MyMessageHandler;

/**
 * Created by Krisez on 2018-01-25.
 */

public class App extends Application {

    private static Context mContext;
    private static User sUser;

    private static App INSTANCE;

    public static App INSTANCE() {
        return INSTANCE;
    }

    private void setInstance(App app) {
        setBmobIMApplication(app);
    }

    private static void setBmobIMApplication(App a) {
        App.INSTANCE = a;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Bmob.initialize(this,"key");

        sUser = BmobUser.getCurrentUser(User.class);

        //TODO 集成：1.8、初始化IM SDK，并注册消息接收器
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
            BmobIM.init(mContext);
            BmobIM.registerDefaultMessageHandler(new MyMessageHandler(mContext));
        }

        // 使用推送服务时的初始化操作
        BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
            @Override
            public void done(BmobInstallation bmobInstallation, BmobException e) {
                if (e == null) {
                    Logger.i(bmobInstallation.getObjectId() + "-" + bmobInstallation.getInstallationId());
                } else {
                    Logger.e(e.getMessage());
                }
            }
        });
        // 启动推送服务
        BmobPush.startWork(this);
    }

    public static Context getContext(){
        return mContext;
    }

    public static User getUser(){
        return sUser;
    }

    public static void setUser(User user){
        sUser = user;
    }

    /**
     * 获取当前运行的进程名
     * @return
     */
    private static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
