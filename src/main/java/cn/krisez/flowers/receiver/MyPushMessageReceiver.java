package cn.krisez.flowers.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Objects;

import cn.bmob.push.PushConstants;
import cn.krisez.flowers.R;
import cn.krisez.flowers.entity.PushBean;
import cn.krisez.flowers.ui.main_ui.MainActivity;

/**
 * Created by Krisez on 2018-03-13.
 */

public class MyPushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Objects.equals(intent.getAction(), PushConstants.ACTION_MESSAGE)){
            String json = intent.getStringExtra("msg");
            Gson gson = new Gson();
            PushBean pushBean = gson.fromJson(json,PushBean.class);
            String msg = pushBean.getAlert();
            Log.d("MyPushMessageReceiver", "onReceive:" + msg);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            intent.setClass(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setTicker("有消息")
                    .setContentTitle("有消息")
                    .setContentText(msg)
                    .setSmallIcon(R.mipmap.icon)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            assert manager != null;
            manager.notify(1, builder.build());
        }
    }
}
