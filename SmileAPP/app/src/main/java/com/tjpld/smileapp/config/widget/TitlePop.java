package com.tjpld.smileapp.config.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tjpld.smileapp.R;
import com.tjpld.smileapp.config.utlis.commonutils.ViewHolder;
import com.tjpld.smileapp.config.utlis.deviceutils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义顶部弹出菜单
 */
public class TitlePop extends PopupWindow implements View.OnClickListener {
    private int mScreenHeight;//屏幕的大小
    private int mScreenWidth;
    private Context mContext;
    private int mLocation[] = new int[2];//位置坐标
    protected final int LIST_PADDING = 15; // 列表弹窗的间隔
    private int popupGravity = Gravity.NO_GRAVITY;// 位置不在中心
    private ListView mListView;//menu的列表
    private boolean mIsDirty;//是否添加列表项
    private Rect mRect;
    private List<ActionItem> items = new ArrayList<ActionItem>();//存放item对象的集合
    private OnItemOnClickListener mItemOnClickListener;
    private OnPopDismissListener mPopDismissListener;
    private WindowManager windowManager;
    private RelativeLayout mPopLayout;
    private LinearLayout mParentLayout;
    public TitlePop(Context context) {
        this(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public TitlePop(Context context, int width, int height) {
        this.mContext = context;
        setFocusable(true);//设置焦点
        setTouchable(true);//设置弹窗内部可以点击
        setOutsideTouchable(true);//点击弹窗外可以关闭
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = windowManager.getDefaultDisplay().getWidth();
        mScreenHeight = windowManager.getDefaultDisplay().getHeight();
        setWidth(width);//设置window的宽高
        setHeight(height);
        setBackgroundDrawable(new BitmapDrawable());//必须要设置 不设置会出问题
        setContentView(LayoutInflater.from(context).inflate(R.layout.title_popup, null));
        //设置弹出的动画
      /*  setAnimationStyle();*/
        //初始化控件

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        initComponent();
    }

    private void initComponent() {
        mPopLayout= (RelativeLayout) getContentView().findViewById(R.id.layout_pop);
        mListView = (ListView) getContentView().findViewById(R.id.title_list);
        mParentLayout= (LinearLayout) getContentView().findViewById(R.id.layout_contain);
        //初始化监听事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //关闭窗口
                if (mItemOnClickListener != null) {
                    mItemOnClickListener.onItemClick(i);
                }
                dismiss();
            }
        });
        mParentLayout.setOnClickListener(this);
    }

    //弹出框显示的位置
    public void show(View view) {
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,view.getHeight()+view.getPaddingTop()+view.getPaddingBottom()+ DeviceUtils.dip2px(mContext,40),0,0);
        params.gravity=Gravity.RIGHT;
        mPopLayout.setLayoutParams(params);
        view.getLocationOnScreen(mLocation);//在屏幕上获得点击位置的坐标点
        mRect = new Rect(mLocation[0], mLocation[1], mLocation[0] + view.getWidth(), mLocation[1] + view.getHeight());//获得矩形的大小，就是这个弹出框的大小
        //判断是否要添加item
        if (mIsDirty) {
            populateActions();
        }
        // 显示弹窗的位置
        showAtLocation(view, popupGravity, mScreenWidth - LIST_PADDING
                - (getWidth() / 2), mRect.bottom + 40);

    }

    //添加item
    private void populateActions() {
        mIsDirty = false;
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                ViewHolder viewHolder = ViewHolder.get(mContext, view, viewGroup, R.layout.list_item_pop, position);
                TextView textView = viewHolder.getView(R.id.txt_title);
                textView.setTextColor(mContext.getResources().getColor(R.color.black));
                textView.setTextSize(16);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                // 设置文本在一行内显示（不换行）
                textView.setSingleLine(true);
                ActionItem item = items.get(position);
                // 设置文本文字
                textView.setText(item.mTitle);
                if (item.mDrawable != null) {
                    textView.setCompoundDrawablePadding(20);//设置文字和图片的间距
                    textView.setCompoundDrawablesWithIntrinsicBounds(item.mDrawable, null, null, null);//在左边添加一个图片
                }
                return viewHolder.getConvertView();
            }
        });
    }

    //向数组中添加actionitem
    public void addActionItem(ActionItem item) {
        if (item != null) {
            mIsDirty = true;
            items.add(item);
        }
    }

    public void addActionItem(Context context, int title, int drawable) {
        ActionItem actionItem = new ActionItem(context, title, drawable);
        if (actionItem != null) {
            mIsDirty = true;
            items.add(actionItem);
        }
    }

    //清除列表
    public void clearAction() {
        if (!items.isEmpty()) {
            items.clear();
            mIsDirty = true;
        }
    }

    /**
     * 根据位置得到子类项
     */
    public ActionItem getAction(int position) {
        if (position < 0 || position > items.size())
            return null;
        return items.get(position);
    }

    /**
     * 设置监听事件
     */
    public void setItemOnClickListener(
            OnItemOnClickListener onItemOnClickListener) {
        this.mItemOnClickListener = onItemOnClickListener;
    }

    public void setOnPopDismissListener(OnPopDismissListener onPopDismissListener) {
        this.mPopDismissListener = onPopDismissListener;
        this.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {//点击外部区域且不是listview时 触发事件
                //在这个事件的情况下做处理但是不拦截
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && view.getId() != R.id.title_list) {
                    mPopDismissListener.onPopDismiss();
                    return false;
                }
                //listview拦截
                if (view.getId() == R.id.title_list) {
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_contain:
                this.dismiss();
                break;
        }
    }

    /**
     * @author guo 功能描述：弹窗子类项按钮监听事件
     */
    public static interface OnItemOnClickListener {
        public void onItemClick(int position);
    }

    public static interface OnPopDismissListener {
        public void onPopDismiss();
    }

    public static class ActionItem {
        public CharSequence mTitle;
        public Drawable mDrawable;

        public ActionItem(CharSequence mTitle, Drawable mDrawable) {
            this.mDrawable = mDrawable;
            this.mTitle = mTitle;
        }


        public ActionItem(Context context, int titleId, int drawId) {
            this.mDrawable = context.getResources().getDrawable(drawId);
            this.mTitle = context.getResources().getString(titleId);
        }


        public ActionItem(Context context, CharSequence title, int drawableId) {
            this.mTitle = title;
            this.mDrawable = context.getResources().getDrawable(drawableId);
        }

        public ActionItem(Context context, CharSequence title) {
            this.mTitle = title;
            this.mDrawable = null;
        }

        public Drawable getmDrawable() {
            return mDrawable;
        }

        public void setmDrawable(Drawable mDrawable) {
            this.mDrawable = mDrawable;
        }

        public CharSequence getmTitle() {
            return mTitle;
        }

        public void setmTitle(CharSequence mTitle) {
            this.mTitle = mTitle;
        }
    }

}
