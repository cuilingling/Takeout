package com.itheima.takeout.dagger.conponent.fragment;

import com.itheima.takeout.dagger.module.fragment.GoodsFragmentModule;
import com.itheima.takeout.ui.fragment.GoodsFragment;

import dagger.Component;

/**
 * 作者：zhaoyang on 2017/7/30 21:36
 */
@Component(modules = GoodsFragmentModule.class)
public interface GoodsFragmentConponent {
    void in(GoodsFragment fragment);
}
