package com.itheima.takeout.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.dagger.conponent.fragment.DaggerGoodsFragmentConponent;
import com.itheima.takeout.dagger.module.fragment.GoodsFragmentModule;
import com.itheima.takeout.model.net.bean.GoodsInfo;
import com.itheima.takeout.model.net.bean.GoodsTypeInfo;
import com.itheima.takeout.presenter.fragment.GoodsFragmentPresenter;
import com.itheima.takeout.ui.adapter.MyGroupAdapter;
import com.itheima.takeout.ui.adapter.MyHeadAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * 作者：zhaoyang on 2017/7/30 21:02
 */

public class GoodsFragment extends Fragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    @InjectView(R.id.lv)
    ListView mLv;
    @InjectView(R.id.shl)
    StickyListHeadersListView mShl;
    @InjectView(R.id.iv_cart)
    ImageView mIvCart;
    @InjectView(R.id.fragment_goods_tv_count)
    TextView mFragmentGoodsTvCount;
    @InjectView(R.id.cart)
    RelativeLayout mCart;
    private Context mContext;

    @Inject
    GoodsFragmentPresenter mPresenter;//注入presenter
    private MyHeadAdapter mHeadAdapter;
    private MyGroupAdapter mGroupAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MyApplication.getContext();
        //注入的引入
        DaggerGoodsFragmentConponent.builder().goodsFragmentModule(new GoodsFragmentModule(this)).build().in(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_goods, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData(getArguments().getLong("seller_id"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    private ArrayList<GoodsInfo> datas = new ArrayList<>();
    private ArrayList<GoodsTypeInfo> headDatas;
    public void success(ArrayList<GoodsTypeInfo> goodsTypeInfo) {
        headDatas = goodsTypeInfo;

        for (int hi = 0; hi < headDatas.size(); hi++) {
            GoodsTypeInfo head = headDatas.get(hi);
            // 普通条目
            for (int di = 0; di < head.list.size(); di++) {
                GoodsInfo data = head.list.get(di);
                data.headId = head.id;
                data.headIndex = hi;
                if (di == 0)
                    head.groupFirstIndex = datas.size();
                datas.add(data);
            }
        }

        mHeadAdapter = new MyHeadAdapter(headDatas);
        mLv.setAdapter(mHeadAdapter);

        mGroupAdapter = new MyGroupAdapter(headDatas, datas);
        mShl.setAdapter(mGroupAdapter);


        // 监听事件设置
        mLv.setOnItemClickListener(this);
        mShl.setOnScrollListener(this);

    }

    /**
     * 左边的条目点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    private boolean isScroll = false;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mHeadAdapter.setSelectedPositon(position);
        GoodsTypeInfo head = headDatas.get(position);
        mShl.setSelection(head.groupFirstIndex);
        isScroll = false;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        isScroll = true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 用户的滚动
        if (isScroll) {

            GoodsInfo data = datas.get(firstVisibleItem);
            System.out.println("data.headIndex:" + data.headIndex);
            // 当前正在置顶显示的头高亮处理
            mHeadAdapter.setSelectedPositon(data.headIndex);

            // 判断头容器对应的条目是否处于可见状态
            // 获取到第一个可见，和最后一个可见的。比第一个小的，或者比最后一个大的均为不可见
            int firstVisiblePosition = mLv.getFirstVisiblePosition();
            int lastVisiblePosition = mLv.getLastVisiblePosition();
            if (data.headIndex <= firstVisiblePosition || data.headIndex >= lastVisiblePosition) {
                mLv.setSelection(data.headIndex);// 可见处理
            }
        }
    }
}
