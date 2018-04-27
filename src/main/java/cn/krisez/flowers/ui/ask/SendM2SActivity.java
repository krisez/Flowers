package cn.krisez.flowers.ui.ask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.krisez.flowers.R;
import cn.krisez.flowers.entity.Send2S;
import cn.krisez.flowers.ui.base.BaseActivity;
import cn.krisez.flowers.ui.base.IDActivity;
import cn.krisez.flowers.utils.SharedPreferenceUtil;


public class SendM2SActivity extends BaseActivity {

    public static final int ASK_CODE = 101;

    private View v;

    private TextView room;
    private EditText name;
    private EditText idcard;
    private TextView sex;
    private TextView nation;
    private EditText place;
    private EditText domicile;
    private EditText phone;
    private TextView people;
    private TextView category;
    private EditText describe;
    private EditText dailiren;
    private EditText dailirenid;
    private RadioGroup dailirentype;

    @Override
    protected View setView() {
        v = View.inflate(this, R.layout.activity_asked, null);
        return v;
    }

    @Override
    protected void initSon() {
        room = (TextView) v.findViewById(R.id.help_room);
        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] s = getResources().getStringArray(R.array.address);
                new AlertDialog.Builder(SendM2SActivity.this)
                        .setTitle("选择")
                        .setItems(s, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                room.setText(s[which] + "援助所");
                            }
                        }).show();
            }
        });
        name = (EditText) v.findViewById(R.id.law_name);
        idcard = (EditText) v.findViewById(R.id.id_card);
        sex = (TextView) v.findViewById(R.id.law_sex);
        //动态判别性别
        idcard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 18) {
                    int ss = s.charAt(16);
                    if (ss % 2 == 0) {
                        sex.setText("女");
                    } else sex.setText("男");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        nation = (TextView) v.findViewById(R.id.law_nation);
        nation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] s = getResources().getStringArray(R.array.nation);
                new AlertDialog.Builder(SendM2SActivity.this)
                        .setTitle("选择")
                        .setItems(s, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nation.setText(s[which]);
                            }
                        }).show();
            }
        });
        place = (EditText) v.findViewById(R.id.law_place);
        domicile = (EditText) v.findViewById(R.id.law_domicile);
        phone = (EditText) v.findViewById(R.id.law_phone);
        people = (TextView) v.findViewById(R.id.law_people);
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] s = getResources().getStringArray(R.array.people_leibie);
                new AlertDialog.Builder(SendM2SActivity.this)
                        .setTitle("选择")
                        .setItems(s, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                people.setText(s[which]);
                            }
                        }).show();
            }
        });
        category = (TextView) v.findViewById(R.id.law_category);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] s = getResources().getStringArray(R.array.help_category);
                new AlertDialog.Builder(SendM2SActivity.this)
                        .setTitle("选择")
                        .setItems(s, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                category.setText(s[which]);
                            }
                        }).show();
            }
        });
        describe = (EditText) v.findViewById(R.id.law_describe);
        dailiren = (EditText) v.findViewById(R.id.law_dailiren);
        dailirenid = (EditText) v.findViewById(R.id.law_dailirenid);
        dailirentype = (RadioGroup) v.findViewById(R.id.law_chooseDlr);
        v.findViewById(R.id.law_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton rb = (RadioButton) v.findViewById(dailirentype.getCheckedRadioButtonId());
                final Send2S mApply = new Send2S();
                mApply.setRoom(room.getText().toString());
                mApply.setName(name.getText().toString());
                mApply.setIdcard(idcard.getText().toString());
                mApply.setSex(sex.getText().toString());
                mApply.setNation(nation.getText().toString());
                mApply.setPlace(place.getText().toString());
                mApply.setDomicile(domicile.getText().toString());
                mApply.setPhone(phone.getText().toString());
                mApply.setPeople(people.getText().toString());
                mApply.setCategory(category.getText().toString());
                mApply.setDailiren(dailiren.getText().toString());
                mApply.setDlrid(dailirenid.getText().toString());
                if (rb != null)
                    mApply.setDlrtype(rb.getText().toString());
                else mApply.setDlrtype("null");
                mApply.setDescribe(describe.getText().toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(SendM2SActivity.this);
                if (mApply.complete()) {
                    builder.setTitle("确定你的信息")
                            .setMessage(mApply.toString())
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferenceUtil.saveApply(mApply);
                                    mApply.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            showError("提交成功！");
                                            finish();
                                        }
                                    });
                                    /*Intent intent = new Intent(SendM2SActivity.this, IDActivity.class);
                                    startActivity(intent);*/
                                }
                            })
                            .setPositiveButton("修改", null)
                            .show();
                } else {
                    builder.setMessage("信息请填写完整！")
                            .setPositiveButton("ok", null).show();
                }

            }
        });
    }

    @Override
    protected String getTitles() {
        return "咨询求助";
    }
}
