package cn.krisez.flowers.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import cn.krisez.flowers.App;
import cn.krisez.flowers.entity.Send2S;
import cn.krisez.flowers.entity.User;

/**
 * Created by Krisez on 2017/10/22.
 */

public class SharedPreferenceUtil {
    /*得到User数据*/
    public static User getUser() {
        /*SharedPreferences sp = App.getContext().getSharedPreferences(App.sp_user, Context.MODE_PRIVATE);
        User user = new User();
        user.setUser(sp.getString("user_user", ""));
        user.setRole(sp.getString("user_role", "1"));
        user.setLogindevice(sp.getString("user_ld", "android"));*/
        return null;
    }

    /*存储User数据*/
    public static void setUser(User user) {
        /*SharedPreferences.Editor editor = App.getContext().getSharedPreferences(App.sp_user, Context.MODE_PRIVATE).edit();
        if (user != null) {
            editor.putString("user_user", user.getUser());
            editor.putString("user_role", user.getRole());
            editor.putString("user_ld", user.getLogindevice());
        } else {
            editor.putString("user_user", "");
            editor.putString("user_role", "1");
            editor.putString("user_ld", "android");
        }
        editor.apply();*/
    }

    /*得到UserInfo数据*/
   /* public static UserInfo getInfo() {
        SharedPreferences sp = App.getContext().getSharedPreferences(App.sp_user_info, Context.MODE_PRIVATE);
        UserInfo info = new UserInfo(new UserInfo.Data());
        info.getData().setNickname(sp.getString("info_name", "点击登录"));
        info.getData().setId(sp.getString("info_id", "000000"));
        info.getData().setSex(sp.getString("info_sex", "f"));
        info.getData().setHeadImgUrl(sp.getString("info_img", "null"));
        info.getData().setRole(sp.getString("info_role", "null"));
        return info;
    }*/

    /*设置UserInfo数据*/
   /* public static void setInfo(UserInfo info) {
        SharedPreferences.Editor editor = App.getContext().getSharedPreferences(App.sp_user_info, Context.MODE_PRIVATE).edit();
        if (info != null) {
            editor.putString("info_name", info.getData().getNickname());
            editor.putString("info_id", info.getData().getId());
            editor.putString("info_sex", info.getData().getSex());
            editor.putString("info_img", info.getData().getHeadImgUrl());
            editor.putString("info_role", info.getData().getRole());
        } else {
            editor.putString("info_name", "点击登录");
            editor.putString("info_id", "000000");
            editor.putString("info_sex", "f");
            editor.putString("info_img", "null");
            editor.putString("info_role", "1");
        }
        editor.apply();
    }*/

    /**
     *
     * 存储申请信息
     */
    public static void saveApply(Send2S mApply){
        SharedPreferences.Editor editor = App.getContext().getSharedPreferences("apply", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String s = gson.toJson(mApply);
        editor.putString("json",s);
        editor.apply();
    }

    /**
     * 取出信息
     */
    public static Send2S getApply(){
        SharedPreferences sharedPreferences = App.getContext().getSharedPreferences("apply", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        return gson.fromJson(sharedPreferences.getString("json",""),Send2S.class);
    }

    public static void EnsureRule(){
        SharedPreferences.Editor editor = App.getContext().getSharedPreferences("rules", Context.MODE_PRIVATE).edit();
        editor.putBoolean("ok",true);
        editor.apply();
    }

    public static boolean mEnsureRule(){
        SharedPreferences sharedPreferences = App.getContext().getSharedPreferences("rules", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("ok",false);
    }
}
