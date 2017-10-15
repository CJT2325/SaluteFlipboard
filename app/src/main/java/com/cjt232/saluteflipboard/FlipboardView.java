package com.cjt232.saluteflipboard;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by cjt on 2017/10/15.
 */

public class FlipboardView extends FrameLayout {

    FlipboardAboveView aboveView;
    FlipboardBelowView belowView;

    public FlipboardView(@NonNull Context context) {
        this(context, null);
    }

    public FlipboardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlipboardView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAnimation();
    }

    private void initView() {
        aboveView = new FlipboardAboveView(getContext());
        belowView = new FlipboardBelowView(getContext());

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        aboveView.setLayoutParams(params);
        belowView.setLayoutParams(params);

        addView(aboveView);
        addView(belowView);
    }

    //上半部分折叠旋转
    public void rotateAboveX(int degrees) {
        aboveView.setRotateX(degrees);
    }

    //下半部分折叠旋转
    public void rotateBelowX(int degrees) {
        belowView.setRotateX(degrees);
    }

    //整体旋转
    public void rotateZ(int degrees) {
        aboveView.setRotateZ(degrees);
        belowView.setRotateZ(degrees);
    }

    AnimatorSet set;
    ValueAnimator animAboveUp;
    ValueAnimator animAboveDown;
    ValueAnimator animBelowUp;
    ValueAnimator animBelowDown;
    ValueAnimator animDown;
    ValueAnimator animRoate;


    private void initAnimation() {
        int duration = 800;

        set = new AnimatorSet();
        animAboveUp = ValueAnimator.ofFloat(0, 20);
        animAboveUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                aboveView.setRotateX((float) animation.getAnimatedValue());
            }
        });
        animAboveDown = ValueAnimator.ofFloat(20, 0);
        animAboveDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                aboveView.setRotateX((float) animation.getAnimatedValue());
            }
        });
        animBelowUp = ValueAnimator.ofFloat(0, 20);
        animBelowUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                belowView.setRotateX((float) animation.getAnimatedValue());
            }
        });
        animBelowDown = ValueAnimator.ofFloat(20, 0);
        animBelowDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                belowView.setRotateX((float) animation.getAnimatedValue());
            }
        });
        animDown = ValueAnimator.ofFloat(20, 0);
        animDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                aboveView.setRotateX((float) animation.getAnimatedValue());
                belowView.setRotateX((float) animation.getAnimatedValue());
            }
        });
        animRoate = ValueAnimator.ofInt(0, 270);
        animRoate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                aboveView.setRotateZ((int) animation.getAnimatedValue());
                belowView.setRotateZ((int) animation.getAnimatedValue());
            }
        });
        animAboveUp.setDuration(duration);
        animAboveDown.setDuration(duration);
        animBelowUp.setDuration(duration);
        animBelowDown.setDuration(duration);
        animDown.setDuration(duration);
        animRoate.setDuration(2000);
        set.playSequentially(animBelowUp, animRoate, animAboveUp, animDown);
    }

    public void startAnim() {
        set.start();
    }
}
