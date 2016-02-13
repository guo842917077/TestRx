package com.tjpld.yuanhangrxjava.main;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.tjpld.yuanhangrxjava.BaseActivity;
import com.tjpld.yuanhangrxjava.R;
import com.tjpld.yuanhangrxjava.config.widget.TitlePop;
import com.tjpld.yuanhangrxjava.report.SubmitInPhotoActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @Bind(R.id.drawer)
    DrawerLayout mDrawer;
    @Bind(R.id.img_rttoolbar)
    ImageButton mBtnMenu;//右上角菜单
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    //抽屉管理器
    private ActionBarDrawerToggle mDrawerToogle;
    private TitlePop mTitlePop;
    //-------------------常量值------------------------
    private final static int TEXT_MODIFY = 0;//图文上报
    private final static int VIDEO_MODIFY = 1;//视频上报
    private final static int SIMPLE_MODIFY = 2;//普通人上报

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initActionBar();
        addMenuItem();

        mTitlePop.setItemOnClickListener(new TitlePop.OnItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case TEXT_MODIFY:
                        intent2Activity(SubmitInPhotoActivity.class);
                        break;
                    case SIMPLE_MODIFY:

                        break;
                    case VIDEO_MODIFY:

                        break;
                }
            }
        });
        mTitlePop.setOnPopDismissListener(new TitlePop.OnPopDismissListener() {
            @Override
            public void onPopDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        mDrawerToogle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawer.setDrawerListener(mDrawerToogle);
    }

    private void addMenuItem() {
        mTitlePop = new TitlePop(this);
        mTitlePop.addActionItem(this, R.string.text_photo_modify, R.drawable.img_photo);
        mTitlePop.addActionItem(this, R.string.video_modify, R.drawable.img_video);
        mTitlePop.addActionItem(this, R.string.text_simple_modify, R.drawable.img_video);
    }

    //设置标题栏
    private void initActionBar() {
        mBtnMenu.setBackgroundResource(R.drawable.ic_action_menu);
        mBtnMenu.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("远航安全助手");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //noinspection SimplifiableIfStatement
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (mDrawer.isDrawerOpen(GravityCompat.START)
                    ) {
                mDrawer.closeDrawers();
            } else {
                mDrawer.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.img_rttoolbar)
    public void popMenu() {
        mTitlePop.show(mBtnMenu);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.6f;
        getWindow().setAttributes(lp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mDrawerToogle.syncState();
        return super.onPrepareOptionsMenu(menu);
    }
}
