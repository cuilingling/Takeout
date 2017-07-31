package com.itheima.takeout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.model.dao.bean.AddressBean;
import com.itheima.takeout.model.dao.bean.UserBean;
import com.itheima.takeout.presenter.activity.AddressPresenter;
import com.itheima.takeout.ui.adapter.ReceiptAddressAdapter;
import com.j256.ormlite.dao.ForeignCollection;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 *
 */
public class ReceiptAddress extends AppCompatActivity {

    @InjectView(R.id.ib_back)
    ImageButton mIbBack;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.rv_receipt_address)
    RecyclerView mRvReceiptAddress;
    @InjectView(R.id.tv_add_address)
    TextView mTvAddAddress;
    private int mUserbeanId;
    private AddressPresenter mPresenter = new AddressPresenter();
    private UserBean mUserBean;
    private ReceiptAddressAdapter mAdapter;
    private ForeignCollection<AddressBean> mAddressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_address);
        ButterKnife.inject(this);

        //拿到数据库中userbean的数据
        Intent intent = getIntent();
        mUserbeanId = intent.getIntExtra("userbeanId", -1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //显示数据时需要先查询数据库中的地址数据并且传递给adapter
        mUserBean = mPresenter.queryDB(3);
        mAddressList = mUserBean.addressList;
        Log.i("地址集合的大小", "onCreate: "+ mAddressList.size());
        //为recycleView设置适配器和线性布局管理器
        mRvReceiptAddress.setLayoutManager(new LinearLayoutManager(MyApplication.getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new ReceiptAddressAdapter(mAddressList, MyApplication.getContext());
        mRvReceiptAddress.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 收货地址界面的中的点击事件
     *
     * @param view
     */
    @OnClick({R.id.ib_back, R.id.tv_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_add_address:
                //增加地址给一个用户
                Intent intent = new Intent(MyApplication.getContext(), AddAddressActivity.class);
                intent.putExtra("userbean", mUserBean);
                startActivity(intent);
                break;
        }
    }
}
