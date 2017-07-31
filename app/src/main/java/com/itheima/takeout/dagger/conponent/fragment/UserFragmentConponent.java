package com.itheima.takeout.dagger.conponent.fragment;

import com.itheima.takeout.dagger.module.fragment.UserFragmentModule;
import com.itheima.takeout.ui.fragment.UserFragment;

import dagger.Component;
/**
 * 作者：zhaoyang on 2017/7/24 10:33
 */
@Component(modules = UserFragmentModule.class)
public interface UserFragmentConponent {
    void in(UserFragment fragment);
}
