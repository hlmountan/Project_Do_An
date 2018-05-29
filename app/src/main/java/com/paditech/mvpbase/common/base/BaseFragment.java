package com.paditech.mvpbase.common.base;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paditech.mvpbase.R;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by Nha Nha on 6/26/2017.
 */

public abstract class BaseFragment extends Fragment {
    //protected View mRootView;
    private WeakReference<Activity> mWeakRef;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mWeakRef = new WeakReference<Activity>((Activity) context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      /*  try {
            if (mRootView == null) {
                mRootView = inflater.inflate(getContentView(), container, false);
                initView(mRootView);
                return mRootView;
            } else return mRootView;
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            View view = LayoutInflater.from(getContext()).inflate(getContentView(), container, false);
            ButterKnife.bind(this, view);
            initView(view);
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract int getContentView();

    protected abstract void initView(View view);

    public void runOnUiThread(Runnable runnable) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(runnable);
        }
    }

    public WeakReference<Activity> getmWeakRef() {
        return mWeakRef;
    }

    public Activity getActivityReference() {
        return mWeakRef != null ? mWeakRef.get() : null;
    }

    public void switchFragment(android.support.v4.app.FragmentManager manager, Fragment fragment, int res) {
        try {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(res, fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
