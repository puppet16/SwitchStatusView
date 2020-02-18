package com.luck.switchstatusview.statusview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.luck.switchstatusview.R;


/**
 * ============================================================
 * 作 者 : 李桐桐
 * 创建日期 ： 2020/2/3
 * 描 述 :消息首页局部等待页面
 * ============================================================
 **/
public class NoticeLoadingLocalView extends LinearLayout {
    private ImageView mIvLoading1;
    private ImageView mIvLoading2;
    private ImageView mIvLoading3;
    private TextView mTvContent;

    private AnimatorSet mAnimatorSet1;
    private AnimatorSet mAnimatorSet2;
    private AnimatorSet mAnimatorSet3;

    private CountDownTimer mTimer;
    private int tempI = 1;

    public NoticeLoadingLocalView(Context context) {
        super(context);
        initView();
    }

    public NoticeLoadingLocalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public NoticeLoadingLocalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_notice_status_loading_local, this);
        mIvLoading1 = findViewById(R.id.iv_notice_loading_local_1);
        mIvLoading2 = findViewById(R.id.iv_notice_loading_local_2);
        mIvLoading3 = findViewById(R.id.iv_notice_loading_local_3);
        mTvContent = findViewById(R.id.notice_loading_local_tv_content);
    }

    /**
     * 返回一个动画集
     *
     * @param iv
     * @return
     */
    private AnimatorSet getAnimatorSet(ImageView iv) {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(iv, "alpha", 1f, 0f, 1f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(iv, "scaleX", 1f, 0f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(iv, "scaleY", 1f, 0f, 1f);
        alphaAnimator.setDuration(1000).setRepeatCount(ValueAnimator.INFINITE);
        scaleXAnimator.setDuration(1000).setRepeatCount(ValueAnimator.INFINITE);
        scaleYAnimator.setDuration(1000).setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alphaAnimator).with(scaleXAnimator).with(scaleYAnimator);
        return animatorSet;
    }

    private void startLoadingAnimation() {
        if (mAnimatorSet1 == null) {
            mAnimatorSet1 = getAnimatorSet(mIvLoading1);
        }
        if (mAnimatorSet2 == null) {
            mAnimatorSet2 = getAnimatorSet(mIvLoading2);
        }
        if (mAnimatorSet3 == null) {
            mAnimatorSet3 = getAnimatorSet(mIvLoading3);
        }
        mTimer = new CountDownTimer((long) (4 * 200), 200) {
            @Override
            public void onTick(long l) {
                if (tempI == 1) {
                    mAnimatorSet1.start();
                } else if (tempI == 2) {
                    mAnimatorSet2.start();
                } else {
                    mAnimatorSet3.start();
                }

                tempI++;
            }

            @Override
            public void onFinish() {

            }
        };
        mTimer.start();
    }

    public void setContent(String content) {
        if (mTvContent != null) {
            mTvContent.setText(content);
        }
    }

    public void showContent(boolean bVisible) {
        if (bVisible) {
            mTvContent.setVisibility(View.VISIBLE);
        } else {
            mTvContent.setVisibility(View.GONE);
        }
    }

    public void startAnimator() {
        cancelAnimator();
        startLoadingAnimation();
    }

    public void cancelAnimator() {
        tempI = 1;
        if (mTimer != null) {
            mTimer.onFinish();
            mTimer.cancel();
            mTimer = null;
        }
        if (mAnimatorSet1 != null) {
            mAnimatorSet1.cancel();
            mAnimatorSet1 = null;
        }
        if (mAnimatorSet2 != null) {
            mAnimatorSet2.cancel();
            mAnimatorSet1 = null;
        }
        if (mAnimatorSet3 != null) {
            mAnimatorSet3.cancel();
            mAnimatorSet1 = null;
        }
    }
}
