package com.itheima.takeout.dagger.module.fragment;

import com.itheima.takeout.presenter.fragment.GoodsFragmentPresenter;
import com.itheima.takeout.ui.fragment.GoodsFragment;

import dagger.Module;
import dagger.Provides;

/**
 * 作者：zhaoyang on 2017/7/30 21:33
 */
@Module
public class GoodsFragmentModule {
    private GoodsFragment mFragment;

    public GoodsFragmentModule(GoodsFragment fragment) {
        mFragment = fragment;
    }
    @Provides
    GoodsFragmentPresenter privideGoodsFragmentPresenter() {
        return new GoodsFragmentPresenter(mFragment);
    }
}
