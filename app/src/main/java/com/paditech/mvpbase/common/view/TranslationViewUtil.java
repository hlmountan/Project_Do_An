package com.paditech.mvpbase.common.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.paditech.mvpbase.R;


/**
 * Created by Nha Nha on 2/28/2018.
 */

public class TranslationViewUtil {
    public final static int DURATION = 800;
    private int mWidthScreen, mHeightScreen, mMinWidth, mMinHeight, mScrollY, mCurrentScrollY;
    private float mPosX;
    private boolean mIsExpand, mIsAniming;
    private int mCardMargin;
    private LockScrollView scrollView;
    private FrameLayout mFrameLayout;
    private LinearLayout.LayoutParams mLayoutParams;
    private Animator.AnimatorListener mAnimationListener;
    private int mMargin, mTopPadding;
    private View mViewTop;
    private View mVisibleView, mHideView;
    private int hidePadding;

    public void setHidePadding(int hidePadding) {
        this.hidePadding = hidePadding;
    }

    public void setmAnimationListener(Animator.AnimatorListener mAnimationListener) {
        this.mAnimationListener = mAnimationListener;
    }

    public void setmVisibleView(View mVisibleView) {
        this.mVisibleView = mVisibleView;
    }

    public void setmViewTop(View mViewTop) {
        if (this.mViewTop == null) {
            this.mViewTop = mViewTop;
            this.mTopPadding = mViewTop.getPaddingTop() + mViewTop.getContext().getResources().getDimensionPixelSize(R.dimen.status_bar_size);
        }
    }

    public void setmHideView(View mHideView) {
        this.mHideView = mHideView;
    }

    public void setmTopPadding(int mTopPadding) {
        this.mTopPadding = mTopPadding;
    }

    public TranslationViewUtil(FrameLayout frameLayout, LockScrollView scrollView, int mWidthScreen, int mHeightScreen) {
        this.scrollView = scrollView;
        this.mFrameLayout = frameLayout;
        this.mWidthScreen = mWidthScreen;
        this.mHeightScreen = mHeightScreen;
        mCardMargin = scrollView.getContext().getResources().getDimensionPixelSize(R.dimen.card_margin);
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            mMargin = ((LinearLayout.LayoutParams) layoutParams).leftMargin;
            mLayoutParams = (LinearLayout.LayoutParams) layoutParams;
        }
    }

    public void setmCardMargin(int mCardMargin) {
        this.mCardMargin = mCardMargin;
    }

    public void animateList() {
        if (mIsAniming) return;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(!mIsExpand ? 0f : 1f, !mIsExpand ? 1f : 0f);
        valueAnimator.setDuration(Math.max(DURATION, mScrollY));
        valueAnimator.setInterpolator(new AnticipateOvershootInterpolator(1.5f, 1f));
        final int mMinW;
        final int mMinH;
        if (!mIsExpand) {
            mCurrentScrollY = scrollView.getScrollY();
            mScrollY = mFrameLayout.getTop() - mCurrentScrollY + hidePadding;
            mMinW = mFrameLayout.getMeasuredWidth();
            mMinH = mFrameLayout.getMeasuredHeight();
            mMinWidth = mMinW;
            mMinHeight = mMinH;
            mPosX = mFrameLayout.getX();
        } else {
            mMinW = mMinWidth;
            mMinH = mMinHeight;
        }
        final int mDeltaW = mWidthScreen - mMinW + mCardMargin * 2 + mMargin;
        final int mDeltaH = mHeightScreen - mMinH;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                scrollView.setScrollY((int) (mCurrentScrollY + mScrollY * ((float) animation.getAnimatedValue())));
                mFrameLayout.setX(mPosX - ((float) animation.getAnimatedValue()) * (mPosX + mCardMargin));
                mFrameLayout.getLayoutParams().height = (int) (mMinH + mDeltaH * ((float) animation.getAnimatedValue()));
                mFrameLayout.getLayoutParams().width = (int) (mMinW + mDeltaW * ((float) animation.getAnimatedValue()));
                if (mMargin > 0 && mLayoutParams != null) {
                    mLayoutParams.leftMargin = (int) (mMargin * ((float) animation.getAnimatedValue()));
                    mLayoutParams.rightMargin = (int) (mMargin * ((float) animation.getAnimatedValue()));
                }
                if (mViewTop != null) {
                    mViewTop.setPadding(0, (int) (mTopPadding * (1 - (float) animation.getAnimatedValue())), 0, 0);
                    mViewTop.requestLayout();
                }
                if (mVisibleView != null) {
                    mVisibleView.setAlpha(((float) animation.getAnimatedValue()));
                    mVisibleView.setScaleX(((float) animation.getAnimatedValue()));
                    mVisibleView.setScaleY(((float) animation.getAnimatedValue()));
                    mVisibleView.requestLayout();
                }

                if (mHideView != null) {
                    mHideView.setAlpha(1 - ((float) animation.getAnimatedValue()));
                    mHideView.setScaleX(1 - ((float) animation.getAnimatedValue()));
                    mHideView.setScaleY(1 - ((float) animation.getAnimatedValue()));
                    mHideView.requestLayout();
                }
                // viewInsuranceTop.setPadding(0, (int) (((float) animation.getAnimatedValue()) * mStatusBarHeight), 0, 0);
                /*btnCloseInsurance.setScaleX(((float) animation.getAnimatedValue()));
                btnCloseInsurance.setScaleY(((float) animation.getAnimatedValue()));*/
                mFrameLayout.requestLayout();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onAnimationStart(Animator animation) {
                if (mAnimationListener != null) mAnimationListener.onAnimationStart(animation);
                super.onAnimationStart(animation);
                mIsAniming = true;
                scrollView.setScrollingEnabled(false);
                scrollView.setSmoothScrollingEnabled(false);
                scrollView.setNestedScrollingEnabled(false);

            }

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mAnimationListener != null) mAnimationListener.onAnimationEnd(animation);
                if (!mIsExpand) {
                    scrollView.setScrollingEnabled(false);
                    scrollView.setNestedScrollingEnabled(false);
                    scrollView.setSmoothScrollingEnabled(false);
                    scrollView.setEnabled(false);
                } else {
                    scrollView.setScrollingEnabled(true);
                    scrollView.setNestedScrollingEnabled(true);
                    scrollView.setSmoothScrollingEnabled(true);
                    scrollView.setEnabled(true);

                }
                mIsExpand = !mIsExpand;
                mIsAniming = false;
            }
        });
        valueAnimator.start();

    }

    public boolean ismIsExpand() {
        return mIsExpand;
    }
}
