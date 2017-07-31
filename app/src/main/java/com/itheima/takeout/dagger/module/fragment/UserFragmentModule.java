package com.itheima.takeout.dagger.module.fragment;

import com.itheima.takeout.presenter.fragment.UserFragmentPresenter;
import com.itheima.takeout.ui.fragment.UserFragment;

import dagger.Module;
import dagger.Provides;

/**
 * UserFragment业务类创建
 */
@Module
public class UserFragmentModule {
    private UserFragment fragment;

    public UserFragmentModule(UserFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    UserFragmentPresenter provideUserFragmentPresenter(){
        return new UserFragmentPresenter(fragment);
    }
}
