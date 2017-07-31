package com.itheima.takeout.presenter.activity;

import android.content.Context;
import android.widget.Toast;

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.model.dao.bean.AddressBean;
import com.itheima.takeout.model.dao.bean.UserBean;
import com.itheima.takeout.presenter.BasePresenter;
import com.itheima.takeout.ui.activity.ReceiptAddress;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * 作者：zhaoyang on 2017/7/24 15:23
 * activity中增删改查的业务逻辑类
 */

public class AddressPresenter extends BasePresenter {


    private final Context mContext;

    public AddressPresenter() {
        mContext = MyApplication.getContext();
    }

    /**
     * 查询数据库
     */
    public UserBean queryDB(int userbeanId) {
        try {
            //拿到数据库中的单个对象,就可以去拿地址集合了
            Dao<UserBean, Integer> dao = helper.getDao(UserBean.class);
            UserBean userBean = dao.queryForId(userbeanId);
            return userBean;
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "没有这个人", Toast.LENGTH_SHORT).show();

        }
        return null;
    }


    //增加地址的方法
    public void addAddress(AddressBean addressBean) {
        try {
            Dao<AddressBean, Integer> dao = helper.getDao(AddressBean.class);
            dao.create(addressBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除地址的方法
    public void deleteAddress(AddressBean addressBean) {
        try {
            Dao<AddressBean, Integer> dao = helper.getDao(AddressBean.class);
            dao.delete(addressBean);
            Toast.makeText(mContext, "成功删除了这条数据", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //修改数据库的方法
    public void updateAddress(AddressBean addressBean) {
        try {
            Dao<AddressBean, Integer> dao = helper.getDao(AddressBean.class);
            dao.update(addressBean);
            Toast.makeText(mContext, "修改数据成功", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void failed(String msg) {

    }

    @Override
    protected void parserData(String data) {

    }
}
