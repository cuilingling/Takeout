package com.itheima.takeout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.takeout.R;
import com.itheima.takeout.ui.fragment.GoodsFragment;
import com.itheima.takeout.ui.fragment.RecommendFragment;
import com.itheima.takeout.ui.fragment.SellerFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SellerDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.back)
    ImageView mBack;
    @InjectView(R.id.tv_goods_title)
    TextView title;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.tabs)
    TabLayout mTabs;
    @InjectView(R.id.vp)
    ViewPager mVp;
    @InjectView(R.id.seller_detail_container)
    FrameLayout mSellerDetailContainer;

    private long sellerId;
    private String[] titles = new String[]{"商品", "评价", "商家"};
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);
        ButterKnife.inject(this);
        Intent intent = getIntent();

        long sellerId = intent.getLongExtra("seller_id", -1);
        String name = intent.getStringExtra("name");
        title.setText(name);
        mBack.setOnClickListener(this);

        mVp.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mTabs.setupWithViewPager(mVp);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {//finish当前的界面
        onBackPressed();
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new GoodsFragment();
                    Bundle arguments = new Bundle();
                    arguments.putLong("seller_id", sellerId);
                    fragment.setArguments(arguments);
                    break;
                case 1:
                    fragment = new RecommendFragment();
                    break;
                case 2:
                    fragment = new SellerFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
