package cn.krisez.flowers.ui.main_ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.exception.BmobException;
import cn.krisez.flowers.App;
import cn.krisez.flowers.R;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.im.ui.ChatActivity;
import cn.krisez.flowers.im.ui.ContactActivity;
import cn.krisez.flowers.im.NewsFrag;
import cn.krisez.flowers.utils.BVCloseShift;
import cn.krisez.flowers.utils.IMMLeaks;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Menu menu;
    private Toolbar toolbar;

    FragmentManager manager;
    FragmentTransaction transaction;

    private LookFrag mLookFrag;
    private PersonFrag mPersonFrag;
    private HelperFrag mHelperFrag;
    private NewsFrag mNewsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        initView();
        if (App.getUser() != null)
            connect();
        IMMLeaks.fixFocusedViewLeak(getApplication());
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("大渡口∨");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddress();
            }
        });
        setSupportActionBar(toolbar);

        mLookFrag = new LookFrag();
        mPersonFrag = new PersonFrag();
        mHelperFrag = new HelperFrag();
        mNewsFrag = new NewsFrag();
        transaction.replace(R.id.frame, mHelperFrag);
        transaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        BVCloseShift.disableShiftMode(navigation);
    }

    private void showAddress() {
        final String[] addr = getResources().getStringArray(R.array.address);
        final int[] pos = {0};
        @SuppressLint("ResourceType") final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.color.transparent);
        builder.setSingleChoiceItems(addr, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pos[0] = which;
                if (which > 8) {
                    Toast toast = Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setText("暂未开通！");
                    toast.show();
                }
            }
        })
                .setTitle("地区")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toolbar.setTitle(addr[pos[0]] + "∨");

                    }
                })
                .setPositiveButton("取消", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        toolbar.findViewById(R.id.toolbar_search).setVisibility(View.GONE);
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.edit).setVisible(false);
        menu.findItem(R.id.contacts).setVisible(false);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            /*if (App.existUser()) {
                Intent intent = new Intent(MainActivity.this, DynamicsActivity.class);
                startActivityForResult(intent, 1);
            } else {
                Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
            }*/
        } else if (id == R.id.edit) {
            if (App.getUser() != null) {
                @SuppressLint("InflateParams") final LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.change_info, null);
                new AlertDialog.Builder(this)
                        .setTitle("个人信息")
                        .setView(linearLayout)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText editText = (EditText) linearLayout.findViewById(R.id.change_name);
                                RadioGroup radioGroup = (RadioGroup) linearLayout.findViewById(R.id.change_sex);
                                String name = editText.getText().toString();
                                RadioButton b = (RadioButton) linearLayout.findViewById(radioGroup.getCheckedRadioButtonId());
                                String sex = b.getText().toString();
                                User user = App.getUser();
                                user.setNickname(name);
                                user.setSex(sex);
                                Intent intent = new Intent();
                                intent.putExtra("name", name);
                                intent.putExtra("sex", sex);
                                mPersonFrag.onActivityResult(0, 0, intent);
                            }
                        }).setNegativeButton("取消", null)
                        .show();
            } else Toast.makeText(this, "请先登录!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.contacts) {
            if (App.getUser() != null) {
                Intent intent = new Intent(this, ContactActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "请先登录!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.bottom_look:
                toolbar.findViewById(R.id.toolbar_search).setVisibility(View.VISIBLE);
                menu.findItem(R.id.add).setVisible(false);
                menu.findItem(R.id.edit).setVisible(false);
                menu.findItem(R.id.contacts).setVisible(false);
                transaction.replace(R.id.frame, mLookFrag);
                transaction.commit();
                return true;
            case R.id.bottom_news:
                toolbar.findViewById(R.id.toolbar_search).setVisibility(View.GONE);
                menu.findItem(R.id.add).setVisible(false);
                menu.findItem(R.id.edit).setVisible(false);
                menu.findItem(R.id.contacts).setVisible(true);
                transaction.replace(R.id.frame, mNewsFrag);
                transaction.commit();
                return true;
            case R.id.bottom_helper:
                toolbar.findViewById(R.id.toolbar_search).setVisibility(View.GONE);
                menu.findItem(R.id.add).setVisible(false);
                menu.findItem(R.id.edit).setVisible(false);
                menu.findItem(R.id.contacts).setVisible(false);
                transaction.replace(R.id.frame, mHelperFrag);
                transaction.commit();
                return true;
            case R.id.bottom_me:
                toolbar.findViewById(R.id.toolbar_search).setVisibility(View.GONE);
                menu.findItem(R.id.add).setVisible(false);
                menu.findItem(R.id.edit).setVisible(true);
                menu.findItem(R.id.contacts).setVisible(false);
                transaction.replace(R.id.frame, mPersonFrag);
                transaction.commit();
                return true;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 160) {
            connect();
        }
    }

    private void connect() {
        final User user = App.getUser();
        BmobIM.connect(user.getObjectId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                    //TODO 会话：3.6、更新用户资料，用于在会话页面、聊天页面以及个人信息页面显示
                    BmobIM.getInstance().
                            updateUserInfo(new BmobIMUserInfo(user.getObjectId(),
                                    user.getNickname(), user.getHeadUrl()));
                } else {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //TODO 连接：3.3、监听连接状态，可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
                Log.d("MainActivity", "onChange:" + status.getMsg());
                Logger.i(BmobIM.getInstance().getCurrentStatus().getMsg());
            }
        });
    }
}
