package com.itheima.takeout.presenter.fragment;

import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima.takeout.MyApplication;
import com.itheima.takeout.model.dao.DBHelper;
import com.itheima.takeout.model.dao.bean.UserBean;
import com.itheima.takeout.model.net.bean.ResponseInfo;
import com.itheima.takeout.model.net.bean.User;
import com.itheima.takeout.presenter.BasePresenter;
import com.itheima.takeout.ui.fragment.UserFragment;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import retrofit2.Call;

/**
 * 作者：zhaoyang on 2017/7/24 02:39
 */

public class UserFragmentPresenter extends BasePresenter {
    private UserFragment mUserFragment;
    private String username;
    private String password;
    //数据库中的id
    public int mUserBeanId;

    public UserFragmentPresenter(UserFragment userFragment) {
        this.mUserFragment = userFragment;
    }


    //获取数据
    public void getData() {
        //模拟数据
        username = "cui";
        password = "12345678";
        Call<ResponseInfo> call = responseInfoAPI.login(username, password);
        call.enqueue(new CallbackAdapter());

    }

    @Override
    protected void parserData(String data) {
        Gson gson = new Gson();
        User info = gson.fromJson(data, User.class);
        mUserFragment.success(info);// 更新界面
        //解析网络请求数据并且进行赋值,存入数据库:
        int id = info.getId();
        float balance = info.getBalance();
        int discount = info.getDiscount();
        int integral = info.getIntegral();
        String name = info.getName();
        String phone = info.getPhone();

        try {
            Dao<UserBean, Integer> dao = helper.getDao(UserBean.class);
            //给userbean赋值
            UserBean userBean = new UserBean();
//            userBean.set_id(2);
            userBean.balance = balance;
            userBean.discount = discount;
            userBean.integral = integral;
            userBean.name = name;
            userBean.phone = phone;
            userBean.login = true;
            //创建一个用户列表
            dao.create(userBean);
            //数据库中的id
            mUserBeanId = userBean.get_id();
            Toast.makeText(MyApplication.getContext(), "数据库创建用户成功"+mUserBeanId, Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(MyApplication.getContext(), "数据库创建用户失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void failed(String msg) {

    }

}
