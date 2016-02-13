package com.tjpld.yuanhangrxjava.config.widget;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.tjpld.yuanhangrxjava.R;


/**
 *
 */
public class SelectPicPopupWindow extends PopupWindow {
    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private RelativeLayout mMenuView;
    private LayoutTransition mLayoutTransition;
    private Animation mAnimationShow;
    private LayoutAnimationController mAnimationController;

    public SelectPicPopupWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = (RelativeLayout) inflater.inflate(R.layout.popupwindow_photos, null);
        btn_take_photo = (Button) mMenuView.findViewById(R.id.btn_take_photo);
        btn_pick_photo = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        mLayoutTransition = new LayoutTransition();
        mAnimationShow = AnimationUtils.loadAnimation(context, R.anim.anim_popshow);
        mAnimationController = new LayoutAnimationController(mAnimationShow);
        mMenuView.setLayoutAnimation(mAnimationController);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_pick_photo.setOnClickListener(itemsOnClick);
        btn_take_photo.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuView);

        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);

        this.setFocusable(true);

        ColorDrawable dw = new ColorDrawable(0xb0000000);

        this.setBackgroundDrawable(dw);
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

}
