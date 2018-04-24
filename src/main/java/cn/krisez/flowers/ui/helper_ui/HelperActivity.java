package cn.krisez.flowers.ui.helper_ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.krisez.flowers.R;
import cn.krisez.flowers.presenter.helper_presenter.HelpPresenter;
import cn.krisez.flowers.presenter.helper_presenter.IHelpPresenter;
import cn.krisez.flowers.ui.base.BaseActivity;

public class HelperActivity extends BaseActivity implements IHelperView {

    private EditText mPerson;

    private TextView mCategory;
    private ArrayAdapter adapter;
    private List<String> types;

    private EditText mDescribe;
    private IHelpPresenter mPresenter;


    @Override
    protected View setView() {
        return View.inflate(this, R.layout.activity_helper, null);
    }


    @Override
    protected void initSon() {
        mPresenter = new HelpPresenter(this);
        mPerson = findViewById(R.id.apply_person);
        mCategory = findViewById(R.id.apply_category);
        mDescribe = findViewById(R.id.apply_describe);

        //spinner初始化
        initSpinner();

        findViewById(R.id.apply_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getName().isEmpty() && !getType().isEmpty() && !getDescribe().isEmpty()) {
                    mPresenter.submit();
                    showProgress();
                }else showError("请输入完整~");
            }
        });
    }

    private void initSpinner() {
        mCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] s = getResources().getStringArray(R.array.help_category);
                new AlertDialog.Builder(HelperActivity.this)
                        .setTitle("选择")
                        .setItems(s, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCategory.setText(s[which]);
                            }
                        }).show();
            }
        });
    }

    @Override
    protected String getTitles() {
        return "申请";
    }

    @Override
    public String getName() {
        return mPerson.getText().toString();
    }

    @Override
    public String getType() {
       return mCategory.getText().toString();
    }

    @Override
    public String getDescribe() {
        return mDescribe.getText().toString();
    }

    @Override
    public void over() {
        missProgress();
        Toast.makeText(this, "提交成功！", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void failed(String s) {
        showError(s);
        missProgress();
    }
}
