package com.tjpld.smileapp.smile.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tjpld.smileapp.R;
import com.tjpld.smileapp.smile.presenter.ISubmitPresenter;
import com.tjpld.smileapp.smile.presenter.SubmitCompl;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SubmitSmileActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.img_lvtoolbar)
    ImageButton mImageToolbarLeft;
    @Bind(R.id.toolbar_leftTv)
    TextView mTvToolbarLeft;
    @Bind(R.id.toolbar_title)
    TextView mTvToolbarTitle;
    @Bind(R.id.toolbar_rightTv)
    TextView mTvToolbarRigth;
    @Bind(R.id.toolbar)
    Toolbar mToobar;
    @Bind(R.id.et_commit_content)
    EditText mEtCommitConent;
    @Bind(R.id.image_group)
    LinearLayout mLayoutImage;
    @Bind(R.id.imgbtn_photo)
    ImageButton mImagePhoto;
    private ISubmitPresenter presenter;//上传笑话的实现
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_smile);
        ButterKnife.bind(this);
        initToolbar();
        presenter=new SubmitCompl(this);
    }

    private void initToolbar() {
        mToobar = (Toolbar) this.findViewById(R.id.toolbar);
        mTvToolbarTitle = (TextView) this.findViewById(R.id.toolbar_title);
        mTvToolbarRigth = (TextView) this.findViewById(R.id.toolbar_rightTv);
        mTvToolbarLeft = (TextView) this.findViewById(R.id.toolbar_leftTv);
        mImageToolbarLeft = (ImageButton) this.findViewById(R.id.img_lvtoolbar);
        mToobar.setTitle("");
        setSupportActionBar(mToobar);
        mTvToolbarLeft.setText("上传笑话");
        mTvToolbarLeft.setVisibility(View.VISIBLE);
        mTvToolbarRigth.setText("提交");
        mImageToolbarLeft.setBackgroundResource(R.drawable.ic_close);
        mImageToolbarLeft.setVisibility(View.VISIBLE);
        mTvToolbarRigth.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_lvtoolbar:
                finish();
                break;
            case R.id.toolbar_rightTv:
                //提交
                presenter.submitSmileContent("","","");
                break;
        }
    }
}
