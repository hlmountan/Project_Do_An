package com.paditech.mvpbase.common.view;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.paditech.mvpbase.common.utils.CommonUtil;

/**
 * Created by Nha Nha on 12/7/2017.
 */

public class TranslationNestedScrollView extends NestedScrollView {
    private static final int MIN_DISTANCE = 0;
    private static final float MIN_RATIO = 1f;
    private int mPrevScrollY;
    private int mScrollY;

    // Fields that don't need to be saved onSaveInstanceState
    private ObservableScrollViewCallbacks mCallbacks;
    private float updateY;
    private int preOffset;

    public ViewGroup getViewGroupBottom() {
        return viewGroupBottom;
    }

    public void setViewGroupBottom(ViewGroup viewGroupBottom) {
        this.viewGroupBottom = viewGroupBottom;
    }

    private int preOffsetbottom;
    private ImageView mImageView;


    private ViewGroup mViewGroup;
    private LayoutParams mLayoutParams;
    boolean mIsRunAnim;
    private int mSize, mMargin;
    private View mViewPager;
    private int haftWidth;
    private ViewGroup viewGroupBottom;
    private boolean isScrollBottom=false;
    public void setViewPager(View viewPager, int haftWidth) {
        this.mViewPager = viewPager;
        this.haftWidth = haftWidth;

    }

    public void setViewChild(ImageView imageView, ViewGroup viewGroup, int size, int margin) {
        this.mImageView = imageView;
        this.mViewGroup = viewGroup;
        this.mSize = size;
        this.mMargin = margin;
        mLayoutParams = (LayoutParams) viewGroup.getLayoutParams();
    }

    public TranslationNestedScrollView(Context context) {
        super(context);
    }

    public TranslationNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TranslationNestedScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

 /*    @Override
    public void onRestoreInstanceState(Parcelable state) {
        try {
            SavedState ss = (SavedState) state;
            mPrevScrollY = ss.prevScrollY;
            mScrollY = ss.scrollY;
            super.onRestoreInstanceState(ss.getSuperState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        try {
            Parcelable superState = super.onSaveInstanceState();
            SavedState ss = new SavedState(superState);
            ss.prevScrollY = mPrevScrollY;
            ss.scrollY = mScrollY;
            return ss;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onSaveInstanceState();
    }
*/

    private boolean animing;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (hasNoCallbacks() || updateY == 0) {
            updateY = event.getY();
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // set on bottom scroll

//                float deltaYbottom = event.getY()- updateY ;
//                if (isScrollBottom){
//                    preOffsetbottom += deltaYbottom;
//                    if (Math.abs(deltaYbottom) > MIN_DISTANCE) {
//                        int scrollY = (int) Math.abs(preOffsetbottom*MIN_RATIO);
//                        if (mCallbacks!=null)
//                            this.mCallbacks.onScrollChanged(scrollY);
//                        runbottom(scrollY);
//                    }
//                }else preOffsetbottom = 0;

                float deltaY = updateY - event.getY();
                // if scroll to top --> no action
                if (deltaY > 0) return super.onTouchEvent(event);

                if (preOffset == 0 || preOffset + deltaY < 1) {
                    preOffset += deltaY;
                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        int scrollY = (int) Math.abs(preOffset * MIN_RATIO);
                        if (mCallbacks != null)
                            this.mCallbacks.onScrollChanged(scrollY);
                        runChildAnim(scrollY);
                    }
                } else preOffset = 0;
                updateY = event.getY();
                break;
            case MotionEvent.ACTION_UP:

                // when release touch
                if (mCallbacks != null)
                    this.mCallbacks.onRelease();
                onReleaseAnim();
                onReleaseSizeBottom();
                preOffsetbottom = 0 ;
                preOffset = 0;
                updateY = 0;
                break;
            case MotionEvent.ACTION_HOVER_EXIT:
                if (getCurrentScrollY() == 0) {
                    int scrollY = (int) Math.abs(preOffset * MIN_RATIO);
                    if (mCallbacks != null) this.mCallbacks.onScrollChanged(scrollY);
                    runChildAnim(scrollY);
                }
                updateY = event.getY();
                break;
        }


        return super.onTouchEvent(event);
    }


    private void runChildAnim(int scrollY) {
        if (mViewPager != null) runAnimPager(scrollY);
        if (mImageView == null) return;
        float offset = (float) scrollY / (mSize * 2);
        int upSize = (int) ((offset + 1) * mSize);
        int upMargin = (int) ((offset + 1) * mMargin);
        mImageView.getLayoutParams().width = upSize;
        mImageView.getLayoutParams().height = upSize;
        mLayoutParams.topMargin = upMargin;
        mViewGroup.requestLayout();
        mImageView.requestLayout();
    }

    private void onReleaseAnim() {
        if (mViewPager != null) onReleaseSizePager();
        if (mImageView == null) return;
        final int currSize = mImageView.getLayoutParams().width - mSize;
        final int currMargin = mLayoutParams.topMargin - mMargin;
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                int upSize = (int) (currSize * (1 - interpolatedTime)) + mSize;
                int upMargin = (int) (currMargin * (1 - interpolatedTime)) + mMargin;
                mImageView.getLayoutParams().height = upSize;
                mImageView.getLayoutParams().width = upSize;
                mLayoutParams.topMargin = upMargin;
                mViewGroup.requestLayout();
                mImageView.requestLayout();
            }
        };
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIsRunAnim = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsRunAnim = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setDuration(300);
        animation.setInterpolator(new DecelerateInterpolator());
        mImageView.clearAnimation();
        if (!mIsRunAnim)
            mImageView.startAnimation(animation);
    }
    private void runbottom(int scrollY){
        System.out.println("scroll y "+scrollY);
        viewGroupBottom.getLayoutParams().height = scrollY;
        viewGroupBottom.requestLayout();

    }
    private void runAnimPager(int scrollY) {
        if (mViewPager == null) return;
        float offset = (float) scrollY / (haftWidth * 2);
        int upSize = (int) ((offset + 1) * haftWidth);
        Log.e("Scroll", "offet:" + offset);
        mViewPager.getLayoutParams().height = upSize;
        mViewPager.requestLayout();
    }

    private void onReleaseSizePager() {
        if (mViewPager == null) return;
        final int currSize = mViewPager.getLayoutParams().height - haftWidth;
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                mViewPager.getLayoutParams().height = (int) (currSize * (1 - interpolatedTime)) + haftWidth;
                mViewPager.requestLayout();
            }
        };
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animing = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animing = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setDuration(300);
        animation.setInterpolator(new DecelerateInterpolator());
        mViewPager.clearAnimation();
        if (!animing)
            mViewPager.startAnimation(animation);
    }
    private void onReleaseSizeBottom() {
        if (mViewGroup == null) return;
        final int currSize = mViewGroup.getLayoutParams().height ;
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                mViewGroup.getLayoutParams().height = (int) (currSize * (1 - interpolatedTime));
                mViewGroup.requestLayout();
            }
        };
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animing = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animing = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setDuration(300);
        animation.setInterpolator(new DecelerateInterpolator());
        mViewGroup.clearAnimation();
        if (!animing)
            mViewGroup.startAnimation(animation);
    }

    public void setScrollViewCallbacks(ObservableScrollViewCallbacks listener) {
        mCallbacks = listener;
    }

    public int getCurrentScrollY() {
        return mScrollY;
    }

    private boolean hasNoCallbacks() {
        return mCallbacks == null && mImageView == null && mViewPager == null;
    }


    public interface ObservableScrollViewCallbacks {
        void onScrollChanged(int scrollY);

        void onRelease();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        View view = this.getChildAt(this.getChildCount() - 1);
        int diff = (view.getBottom() - (this.getHeight() + this.getScrollY()));

        // if diff is zero, then the bottom has been reached
        if (diff == 0) {
            // do stuff
            isScrollBottom = true;
        }else
            isScrollBottom = false;

    }
}
