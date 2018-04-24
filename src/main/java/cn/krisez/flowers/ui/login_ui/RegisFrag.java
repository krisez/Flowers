package cn.krisez.flowers.ui.login_ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.krisez.flowers.R;
import cn.krisez.flowers.presenter.login_presenter.ILoginPresenter;
import cn.krisez.flowers.presenter.login_presenter.LoginPresenter;


/**
 * Created by Krisez on 2018-02-03.
 */

public class RegisFrag extends Fragment implements ILoginView {
    private AutoCompleteTextView mUser;
    private EditText mPassword;
    private EditText mPhone;
    private EditText mYzm;
    private TextView mGetYzm;
    private RadioGroup mRadioGroup;


    private ILoginPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_login_reg, container, false);
        return v;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new LoginPresenter(this);

        mUser = view.findViewById(R.id.register_user);
        mPassword = view.findViewById(R.id.register_password);
        mPhone = view.findViewById(R.id.register_phone);
        mYzm = view.findViewById(R.id.register_input_yzm);
        mGetYzm = view.findViewById(R.id.register_get_yzm);
        mRadioGroup = view.findViewById(R.id.register_sex);
        final Button signUp = view.findViewById(R.id.login_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getAccount().isEmpty()||getMM().isEmpty()||mPhone.getText().toString().isEmpty()
                        ||mYzm.getText().toString().isEmpty()){
                    showError("请填写完整！");
                }else {
                    RadioButton button = view.findViewById(mRadioGroup.getCheckedRadioButtonId());
                    String sex = button.getText().toString();
                    mPresenter.register(mPhone.getText().toString(),sex);
                    showProgress();
                }
            }
        });

        listenInput();
    }

    private void listenInput() {
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 11) {
                    mGetYzm.setEnabled(true);
                    mGetYzm.setTextColor(0x84ff0005);
                } else {
                    mGetYzm.setEnabled(false);
                    mGetYzm.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mGetYzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "验证码：0000", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public String getAccount() {
        return mUser.getText().toString();
    }

    @Override
    public String getMM() {
        return mPassword.getText().toString();
    }

    @Override
    public void showError(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        ((LoginActivity) getActivity()).showProgress();
    }

    @Override
    public void dismissProgress() {
        ((LoginActivity) getActivity()).dismissProgress();
    }

    @Override
    public void right() {
        ((LoginActivity) getActivity()).success(getAccount());
    }
}
