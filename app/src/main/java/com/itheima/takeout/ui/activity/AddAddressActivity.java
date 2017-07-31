package com.itheima.takeout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.itheima.takeout.presenter.activity.AddressPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddAddressActivity extends AppCompatActivity {

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
    private UserBean mUserBean;
    private AddressPresenter mPresenter = new AddressPresenter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        //当前用户新增的地址
        mUserBean = (UserBean) intent.getSerializableExtra("userbean");

    }

    @OnClick({R.id.ib_back, R.id.ib_delete_phone, R.id.tv_receipt_address, R.id.ib_select_label, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_delete_phone:
                break;
            case R.id.tv_receipt_address:
                break;
            case R.id.ib_select_label:
                break;
            case R.id.bt_ok:
                //确认新增需要添加到数据库中地址
                addAdress();
                break;
        }
    }

    private void addAdress() {
        String name = mEtName.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        String DetailAddress = mEtDetailAddress.getText().toString().trim();
        String ReceiptAddress = mTvReceiptAddress.getText().toString().trim();
        String lable = "banjia";
        int buttonId = mRgSex.getCheckedRadioButtonId();
        String sex = "man";
        if (buttonId == R.id.rb_man) {
             sex = "man";
        } else {
            sex = "woman";
        }
        if (name != null && sex != null && phone != null && ReceiptAddress != null && DetailAddress != null && lable != null) {
            AddressBean addressBean = new AddressBean(name, sex, phone, ReceiptAddress, DetailAddress, lable, 109.99, 34.667);
            addressBean.setUser(mUserBean);
            mPresenter.addAddress(addressBean);
            Toast.makeText(this, "新增地址添加成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "请输入详细的内容", Toast.LENGTH_SHORT).show();
        }
    }
}
