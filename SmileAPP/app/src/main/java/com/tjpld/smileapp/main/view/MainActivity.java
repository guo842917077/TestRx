package com.tjpld.smileapp.main.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tjpld.smileapp.R;
import com.tjpld.smileapp.code.CodeFragment;
import com.tjpld.smileapp.config.widget.FragmentViewPagerAdapter;
import com.tjpld.smileapp.config.widget.MainViewPager;
import com.tjpld.smileapp.config.widget.TitlePop;
import com.tjpld.smileapp.setting.SettingFragment;
import com.tjpld.smileapp.smile.view.SmileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面 负责管理Fragment
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.toolbar)
    public Toolbar mToolbar;
    @Bind(R.id.img_toolbar_left)
    public ImageButton mImgBtnLeft;
    @Bind(R.id.toolbar_title)
    public TextView mToolTitle;
    @Bind(R.id.img_toolbar_right)
    public ImageButton mImageBtnRight;
    @Bind(R.id.radio_smile)
    public RadioButton radio_smile;
    @Bind(R.id.radio_code)
    public RadioButton radio_code;
    @Bind(R.id.radio_setting)
    public RadioButton radio_setting;
    @Bind(R.id.viewpager_main)
    public MainViewPager viewpager_main;
    public List<Fragment> list_fragment;
    public FragmentViewPagerAdapter mFragmentAdapter;
    private TitlePop mTitlePop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initActionBar();
        initComponent();
        initEvent();
        mImgBtnLeft.setOnClickListener(this);//退出事件
        mImageBtnRight.setOnClickListener(this);
    }

    private void initEvent() {
        viewpager_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    radio_code.setTextColor(getResources().getColor(R.color.grayLine));
                    radio_setting.setTextColor(getResources().getColor(R.color.grayLine));
                } else if (position == 1) {
                    radio_code.setTextColor(getResources().getColor(R.color.blueLight));
                    radio_smile.setTextColor(getResources().getColor(R.color.grayLine));
                    radio_setting.setTextColor(getResources().getColor(R.color.grayLine));
                } else if (position == 2) {
                    radio_setting.setTextColor(getResources().getColor(R.color.blueLight));
                    radio_code.setTextColor(getResources().getColor(R.color.grayLine));
                    radio_smile.setTextColor(getResources().getColor(R.color.grayLine));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radio_smile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radio_smile.setTextColor(getResources().getColor(R.color.blueLight));
                    viewpager_main.setCurrentItem(0);
                    radio_code.setChecked(false);
                    radio_setting.setChecked(false);
                } else {
                    radio_smile.setTextColor(Color.BLACK);
                }
            }
        });
        radio_code.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewpager_main.setCurrentItem(1);
                    radio_smile.setChecked(false);
                } else {
                    radio_code.setTextColor(Color.BLACK);
                }
            }
        });
        radio_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewpager_main.setCurrentItem(2);
                    radio_smile.setChecked(false);
                } else {
                    radio_setting.setTextColor(Color.BLACK);
                }
            }
        });
    }

    private void initComponent() {
        list_fragment = new ArrayList<Fragment>();
        list_fragment.add(new SmileFragment());
        list_fragment.add(new CodeFragment());
        list_fragment.add(new SettingFragment());
        mFragmentAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), viewpager_main, list_fragment);
        viewpager_main.setAdapter(mFragmentAdapter);
        mTitlePop = new TitlePop(this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTitlePop.addActionItem(this, R.string.submit_smile_content, R.drawable.img_send);
    }

    //设置标题栏
    private void initActionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.action_title);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mImgBtnLeft.setBackgroundResource(R.drawable.ic_function);
        mImgBtnLeft.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
          switch (R.id.img_toolbar_right){
              case R.id.img_toolbar_right:
                  mTitlePop.show(mImageBtnRight);
                  break;
          }
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        this.overridePendingTransition(R.anim.sild_out_top, R.anim.sild_out_top);
    }
}
