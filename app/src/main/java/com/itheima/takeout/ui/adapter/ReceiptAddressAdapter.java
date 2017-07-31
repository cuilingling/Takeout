package com.itheima.takeout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.model.dao.bean.AddressBean;
import com.itheima.takeout.ui.activity.EditReceiptAddress;
import com.j256.ormlite.dao.ForeignCollection;

import java.io.LineNumberInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 收货地址界面的适配器
 * 作者：zhaoyang on 2017/7/23 20:15
 */

public class ReceiptAddressAdapter extends RecyclerView.Adapter {
    private Context mContext;
    ForeignCollection<AddressBean> addressList;
    ArrayList<AddressBean>  addressBeans = new ArrayList<>();
    private AddressBean mAddressBean;

    public ReceiptAddressAdapter(ForeignCollection<AddressBean> addressList,Context context) {
        this.mContext = context;
        this.addressList = addressList;
        Iterator<AddressBean> iterator = addressList.iterator();
        for (AddressBean addressBean : addressList) {
            addressBeans.add(addressBean);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //item_receipt_address
        View inflate = View.inflate(MyApplication.getContext(), R.layout.item_receipt_address, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mAddressBean = addressBeans.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mTvName.setText(mAddressBean.name);
        viewHolder.mTvSex.setText(mAddressBean.sex);
        viewHolder.mTvPhone.setText(mAddressBean.phone);
        viewHolder.mTvLabel.setText(mAddressBean.label);
        viewHolder.mTvAddress.setText(mAddressBean.receiptAddress);
        viewHolder.mIvEdit.setImageResource(R.drawable.address_icon_edit);
        //编辑的点击事件
        viewHolder.mIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditReceiptAddress.class);
                intent.putExtra("AddressBean", mAddressBean);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList == null ? 0 : addressList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.tv_name)
        TextView mTvName;
        @InjectView(R.id.tv_sex)
        TextView mTvSex;
        @InjectView(R.id.tv_phone)
        TextView mTvPhone;
        @InjectView(R.id.tv_label)
        TextView mTvLabel;
        @InjectView(R.id.tv_address)
        TextView mTvAddress;
        @InjectView(R.id.iv_edit)
        ImageView mIvEdit;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
