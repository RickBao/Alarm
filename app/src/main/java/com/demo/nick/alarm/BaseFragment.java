package com.demo.nick.alarm;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment 基类
 * Created by nick on 18-12-16.
 */

public abstract class BaseFragment extends Fragment {

    /**
     * ButterKnife 8.4.0 do not support ButterKnife.unbind anymore.
     * Instead of the object of Binder.bind return. and use this
     * object`s function unbind() to deal operation.
     */
    private Unbinder mUnBinder;

    /**
     * Required empty public constructor. Subclass do not
     * need to implement their own.
     */
    public BaseFragment() {}

    /**
     * @return the layout resource for this fragment
     */
    @LayoutRes
    protected abstract int contentLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(contentLayout(), container, false);
        mUnBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    public void onPageSelected() {

    }
}
