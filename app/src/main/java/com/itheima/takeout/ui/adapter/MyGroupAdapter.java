package com.itheima.takeout.ui.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.GoodsInfo;
import com.itheima.takeout.model.net.bean.GoodsTypeInfo;
import com.itheima.takeout.utils.NumberFormatUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * 作者：zhaoyang on 2017/7/30 22:02
 */

public class MyGroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private final ArrayList<GoodsTypeInfo> headDataSet;
    private final ArrayList<GoodsInfo> itemDataSet;
    public MyGroupAdapter(ArrayList<GoodsTypeInfo> headDataSet, ArrayList<GoodsInfo> itemDataSet) {
        this.headDataSet = headDataSet;
        this.itemDataSet = itemDataSet;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        TextView head = new TextView(parent.getContext());
        GoodsTypeInfo headData = headDataSet.get(itemDataSet.get(position).headIndex);
        head.setText(headData.name);
        head.setBackgroundColor(MyApplication.getContext().getResources().getColor(R.color.colorItemBg));
        return head;
    }

    @Override
    public long getHeaderId(int position) {
        return itemDataSet.get(position).headId;
    }

    @Override
    public int getCount() {
        return itemDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return itemDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsInfo data = itemDataSet.get(position);
        ItemViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(MyApplication.getContext(), R.layout.item_goods, null);
            holder = new ItemViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        holder.setData(data);
        return convertView;
    }
    class ItemViewHolder {
        View itemView;
        private GoodsInfo data;
        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_zucheng)
        TextView tvZucheng;
        @InjectView(R.id.tv_yueshaoshounum)
        TextView tvYueshaoshounum;
        @InjectView(R.id.tv_newprice)
        TextView tvNewprice;
        @InjectView(R.id.tv_oldprice)
        TextView tvOldprice;
        @InjectView(R.id.ib_minus)
        ImageButton ibMinus;
        @InjectView(R.id.tv_money)
        TextView tvCount;
        @InjectView(R.id.ib_add)
        ImageButton ibAdd;
        private FrameLayout container;
        private TextView count;


        public ItemViewHolder(View itemView) {
            this.itemView = itemView;
            ButterKnife.inject(this, this.itemView);
        }

        public void setData(GoodsInfo data) {
            this.data = data;

            //图片
            Picasso.with(MyApplication.getContext()).load(data.icon).into(ivIcon);
            tvName.setText(data.name);
            if (TextUtils.isEmpty(data.form)) {
                tvZucheng.setVisibility(View.GONE);
            } else {
                tvZucheng.setVisibility(View.VISIBLE);
                tvZucheng.setText(data.form);
            }
            tvYueshaoshounum.setText("月销售" + data.monthSaleNum + "份");
            tvNewprice.setText(NumberFormatUtils.formatDigits(data.newPrice));
            if (data.oldPrice == 0) {
                tvOldprice.setVisibility(View.GONE);
            } else {
                tvOldprice.setVisibility(View.VISIBLE);
                tvOldprice.setText(NumberFormatUtils.formatDigits(data.oldPrice));
                //TextView出现中间的线
                tvOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
