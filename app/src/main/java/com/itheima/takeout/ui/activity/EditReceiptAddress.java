package com.itheima.takeout.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.takeout.R;
import com.itheima.takeout.model.dao.bean.AddressBean;
import com.itheima.takeout.model.dao.bean.UserBean;
import com.itheima.takeout.model.net.bean.User;
import com.itheima.takeout.presenter.activity.AddressPresenter;
import com.j256.ormlite.dao.ForeignCollection;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 新增地址的界面
 */
public class EditReceiptAddress extends Activity {

    @InjectView(R.id.ib_back)
    ImageButton mIbBack;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.ib_delete_address)
    ImageButton mIbDeleteAddress;
    @InjectView(R.id.rl_left)
    TextView mRlLeft;
    @InjectView(R.id.et_name)
    EditText mEtName;
    @InjectView(R.id.rb_man)
    RadioButton mRbMan;
    @InjectView(R.id.rb_women)
    RadioButton mRbWomen;
    @InjectView(R.id.rg_sex)
    RadioGroup mRgSex;
    @InjectView(R.id.et_phone)
    EditText mEtPhone;
    @InjectView(R.id.ib_delete_phone)
    ImageButton mIbDeletePhone;
    @InjectView(R.id.tv_receipt_address)
    TextView mTvReceiptAddress;
    @InjectView(R.id.et_detail_address)
    EditText mEtDetailAddress;
    @InjectView(R.id.tv_label)
    TextView mTvLabel;
    @InjectView(R.id.ib_select_label)
    ImageView mIbSelectLabel;
    @InjectView(R.id.bt_ok)
    Button mBtOk;
    //获得数据库的实例
    private AddressPresenter mPresenter = new AddressPresenter();
    private boolean isadd;
    private AddressBean mAddressBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_receipt_address);
        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        mAddressBean = (AddressBean) intent.getSerializableExtra("AddressBean");
        //如果是编辑地址
        if (mAddressBean != null) {
            mTvTitle.setText("修改地址");
            mEtName.setText(mAddressBean.name);
            if (mAddressBean.sex == "man") {
                mRbMan.setChecked(true);
            } else {
                mRbWomen.setChecked(true);
            }
            mEtPhone.setText(mAddressBean.phone);
            mTvReceiptAddress.setText(mAddressBean.receiptAddress);
            mEtDetailAddress.setText(mAddressBean.detailAddress);
            mTvLabel.setText(mAddressBean.label);
        } else {
            Toast.makeText(this, "需要增加地址", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.ib_back, R.id.ib_delete_address, R.id.ib_delete_phone, R.id.ib_select_label, R.id.bt_ok, R.id.tv_receipt_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_delete_address:
                deleteAddress();
                break;
            case R.id.ib_delete_phone:
                //点击了叉号清楚当前输入框中的内容
                mEtPhone.setText("");
                break;
            case R.id.ib_select_label:
                break;
            case R.id.bt_ok://确定按钮
                commit();
                break;
            case R.id.tv_receipt_address:
                //打开地图
                openMap();
                break;
        }
    }

    /**
     * 删除该条地址内容
     */
    private void deleteAddress() {
        mPresenter.deleteAddress(mAddressBean);
        finish();
    }

    /**
     * 打开地图
     */
    private void openMap() {

    }

    /**
     * 点击确定按钮,提交数据到数据库
     */
    private void commit() {
        mAddressBean.name = mEtName.getText().toString();
        mAddressBean.phone = mEtPhone.getText().toString();
        mAddressBean.receiptAddress = mTvReceiptAddress.getText().toString();
        int radioButtonId = mRgSex.getCheckedRadioButtonId();
        if (radioButtonId == R.id.rb_man) {
            mAddressBean.sex = "man";
        } else {
            mAddressBean.sex = "woman";
        }
        mAddressBean.label = mTvLabel.getText().toString();
        mAddressBean.detailAddress = mEtDetailAddress.getText().toString();
        //修改数据库
        mPresenter.updateAddress(mAddressBean);
        finish();
    }
}
