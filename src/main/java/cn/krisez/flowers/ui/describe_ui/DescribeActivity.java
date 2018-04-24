package cn.krisez.flowers.ui.describe_ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.krisez.flowers.App;
import cn.krisez.flowers.R;
import cn.krisez.flowers.adapter.CommentAdapter;
import cn.krisez.flowers.adapter.OnItemClickListener;
import cn.krisez.flowers.entity.Apply;
import cn.krisez.flowers.entity.Comment;
import cn.krisez.flowers.presenter.describe_presenter.DescribePresenter;
import cn.krisez.flowers.presenter.describe_presenter.IDescribePresenter;
import cn.krisez.flowers.ui.base.BaseActivity;
import cn.krisez.flowers.widget.CircleImageView;
import cn.krisez.flowers.widget.DividerDecoration;
import cn.krisez.flowers.widget.MScrollView;
import cn.krisez.flowers.widget.OnScrollChangedListener;

public class DescribeActivity extends BaseActivity implements IDescribeView, View.OnClickListener {

    private Apply mApply;
    private CircleImageView mHead;
    private TextView mNickname;
    private TextView mDate;
    private ImageView like;
    private TextView mContent;
    private EditText input;
    private Button send;
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private List<Comment> mList;
    private boolean mBooleans[] = new boolean[3];

    private LinearLayout mLinearLayout;
    private IDescribePresenter mPresenter;

    @Override
    protected View setView() {
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return View.inflate(this, R.layout.activity_describe, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initSon() {
        showProgress();
        mLinearLayout = findViewById(R.id.details_linear);
        mPresenter = new DescribePresenter(this);
        mApply = getApply();
        mHead = findViewById(R.id.describe_head);
        mDate = findViewById(R.id.describe_date);
        mNickname = findViewById(R.id.describe_nickname);
        like = findViewById(R.id.describe_likes);
        mContent = findViewById(R.id.describe_content);
        input = findViewById(R.id.describe_input);
        send = findViewById(R.id.describe_send_comment);
        initData();

        final MScrollView scrollView = findViewById(R.id.describe_scroll);
        if (!getApply().isOver()) {
            scrollView.setOnScrollChangedListener(new OnScrollChangedListener() {
                @Override
                public void onScrollChanged(int top, int oldTop) {
                    if (Math.abs(top - oldTop) > 10) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null && imm.isActive()) {
                            imm.hideSoftInputFromWindow(DescribeActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                }
            });
        }
    }

    private void initData() {
        mList = new ArrayList<>();
        mNickname.setText(mApply.getAuthor().getNickname());
        mDate.setText(mApply.getCreatedAt());
        Glide.with(this).load(mApply.getAuthor().getHeadUrl()).into(mHead);
        mContent.setText(mApply.getDescribe());

        mRecyclerView = findViewById(R.id.describe_recycler);
        mAdapter = new CommentAdapter(this, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerDecoration(this, DividerDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                if (getApply().isOver()) {
                    showAlertNo(position);
                }
                if (!getApply().isOver()) {
                    showAlert(position);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        mPresenter.getLists();
        send.setOnClickListener(this);
        like.setOnClickListener(this);
        mPresenter.isLike();

    }

    private void showAlert(final int position) {
        new AlertDialog.Builder(DescribeActivity.this)
                .setTitle(mList.get(position).getAuthor().getNickname())
                .setMessage(mList.get(position).getContent())
                .setPositiveButton("赞同", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Comment comment = mList.get(position);
                        if (comment.getCount() < 2) {
                            comment.setCount(comment.getCount() + 1);
                            comment.update();
                            mList.get(position).setCount(comment.getCount());
                            mAdapter.notifyItemChanged(position);
                            if (comment.getCount() == 2) {
                                showMessage();
                            } else if (mList.size() == 2) {
                                showMessage();
                            }
                        }

                    }
                })
                .setNegativeButton("否决", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        send.setEnabled(true);
                        send.setText(R.string.submit);
                        Toast.makeText(DescribeActivity.this, "请提交您的建议", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    private void showAlertNo(int position) {
        new AlertDialog.Builder(DescribeActivity.this)
                .setTitle(mList.get(position).getAuthor().getNickname())
                .setMessage(mList.get(position).getContent())
                .show();
    }

    @Override
    protected String getTitles() {
        return "详情";
    }

    @Override
    public Apply getApply() {
        return (Apply) getIntent().getSerializableExtra("apply");
    }

    @Override
    public Comment getComment() {
        Comment comment = new Comment();
        comment.setContent(input.getText().toString());
        comment.setApply(getApply());
        comment.setAuthor(App.getUser());
        comment.setCount(0);
        return comment;
    }

    @Override
    public String getObjectId() {
        return getApply().getObjectId();
    }

    @Override
    public void setComments(List<Comment> commentList) {
        for (int i = 0; i < commentList.size(); i++) {
            mBooleans[i] = false;
        }
        mList.addAll(commentList);
        mAdapter.notifyDataSetChanged();
        missProgress();
        if (commentList.size() > 0) {
            send.setEnabled(false);
            send.setText("请先判断");
        }

        if (getApply().isOver()) {
            mLinearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void addComment(Comment comment) {
        mAdapter.addData(0, comment);
        missProgress();
        if (mList.get(1).getCount() == 1) {
            showMessage();
        }
    }

    @Override
    public void likes(boolean b) {
        if (b) {
            like.setImageResource(R.drawable.ic_star_fill);
            missProgress();
        }
    }

    private void showMessage() {
        new AlertDialog.Builder(DescribeActivity.this)
                .setTitle("达标")
                .setMessage("目标已达到,感谢您的配合！\n退出刷新即可看到相应情况！")
                .show();
        Apply apply = getApply();
        apply.setDeal(true);
        apply.setOver(true);
        apply.setWhere("待完结");
        apply.update();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.describe_send_comment:
                if (App.getUser() != null) {
                    if (!input.getText().toString().isEmpty()) {
                        if (mList.size() == 0) {
                            Apply apply = getApply();
                            apply.setDeal(true);
                            apply.setWhere("正处理");
                            apply.update();
                        }
                        mPresenter.submit();
                        showProgress();
                        input.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null && imm.isActive()) {
                            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    } else showError("不能为空");
                } else showError("检查登录状态");
                break;
            case R.id.describe_likes:
                if (App.getUser() != null) {
                    mPresenter.likes();
                    showProgress();
                }else showError("请登录！");
                break;
        }
    }
}
