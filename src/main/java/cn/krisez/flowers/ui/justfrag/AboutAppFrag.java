package cn.krisez.flowers.ui.justfrag;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.krisez.flowers.R;


/**
 * Created by Krisez on 2017-12-07.
 */

public class AboutAppFrag extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_about_app, container, false);
        return v;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = (TextView) view.findViewById(R.id.app_version);
        textView.setText("版本号:"+getVersion());
        view.findViewById(R.id.app_gm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://law.krisez.cn"));
                startActivity(intent);
            }
        });
    }

    private String getVersion() {
        PackageManager pm = getActivity().getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(getActivity().getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "null";
    }
}
