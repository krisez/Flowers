package cn.krisez.flowers.ui.justfrag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.krisez.flowers.App;
import cn.krisez.flowers.R;
import cn.krisez.flowers.entity.Authentication;
import cn.krisez.flowers.entity.User;
import cn.krisez.flowers.ui.base.BaseFragment;
import cn.krisez.flowers.ui.describe_ui.DescribeActivity;

/**
 * Created by Krisez on 2018-03-23.
 */

public class AuthenticationFragment extends BaseFragment {

    @Override
    protected void initOperation(View view) {
        final EditText editText1 = view.findViewById(R.id.authentication_name);
        final EditText editText2 = view.findViewById(R.id.authentication_last);
        final CardView cardView = view.findViewById(R.id.authentication_submit);
        if (App.getUser().getType() == 1) {
            cardView.setEnabled(false);
            cardView.setClickable(false);
        }
        cardView.findViewById(R.id.authentication_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                BmobQuery<Authentication> query = new BmobQuery<>();
                query.addWhereEqualTo("name", editText1.getText().toString());
                query.addWhereEqualTo("last", editText2.getText().toString());
                query.findObjects(new FindListener<Authentication>() {
                    @Override
                    public void done(final List<Authentication> list, BmobException e) {
                        if (e == null && list.size() > 0) {
                            if (list.get(0).getWho() == null) {
                                updateInfo(list, cardView);
                            } else if (list.get(0).getWho().getObjectId().isEmpty()) {
                                updateInfo(list, cardView);
                            } else {
                                showToast("认证错误！");
                                missProgress();
                            }
                        } else {
                            assert e != null;
                            showToast(e.getMessage());
                            missProgress();
                        }
                    }
                });
            }
        });
    }

    private void updateInfo(final List<Authentication> list, final CardView cardView) {
        final User user = App.getUser();
        user.setType(1);//学生认证
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                showToast("认证成功！");
                missProgress();
                cardView.setEnabled(false);
                cardView.setClickable(false);
                Intent intent = new Intent();
                intent.putExtra("authentication", "学生");
                getActivity().setResult(Activity.RESULT_OK, intent);
                Authentication authentication = list.get(0);
                authentication.setWho(user);
                authentication.update();
            }
        });
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected View addView() {
        return View.inflate(getContext(), R.layout.frag_about_authentication, null);
    }

    @Override
    public void onRefresh() {

    }
}
