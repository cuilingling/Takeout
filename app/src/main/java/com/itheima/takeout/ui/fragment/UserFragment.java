package com.itheima.takeout.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.dagger.conponent.fragment.DaggerUserFragmentConponent;
import com.itheima.takeout.dagger.module.fragment.UserFragmentModule;
import com.itheima.takeout.model.net.bean.User;
import com.itheima.takeout.presenter.fragment.UserFragmentPresenter;
import com.itheima.takeout.ui.activity.ReceiptAddress;

import javax.inject.Inject;

/**
 * Created by ywf on 2017/3/24.
 */
public class UserFragment extends Fragment {
    @Inject
    UserFragmentPresenter presenter;
    //数据库中的id
    private int mUserbeanId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dagger2的引入
        DaggerUserFragmentConponent
                .builder()
                .userFragmentModule(new UserFragmentModule(this))
                .build()
                .in(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView address = (ImageView) view.findViewById(R.id.address);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getContext(), ReceiptAddress.class);
                mUserbeanId = presenter.mUserBeanId;
                intent.putExtra("userbeanId", mUserbeanId);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        //数据库中的id
         presenter.getData();
        // 显示滚动条
    }

    /**数据解析成功更新Ui
     * @param info
     */
    public void success(User info) {

    }
}
