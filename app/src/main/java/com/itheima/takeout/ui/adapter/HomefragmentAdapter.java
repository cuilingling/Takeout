package com.itheima.takeout.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.Category;
import com.itheima.takeout.model.net.bean.Head;
import com.itheima.takeout.model.net.bean.HomeInfo;
import com.itheima.takeout.model.net.bean.HomeItem;
import com.itheima.takeout.model.net.bean.Promotion;
import com.itheima.takeout.model.net.bean.Seller;
import com.itheima.takeout.ui.ShoppingCartManager;
import com.itheima.takeout.ui.activity.SellerDetailActivity;
import com.itheima.takeout.utils.Constant;
import com.squareup.picasso.Picasso;

/**
 * 作者：zhaoyang on 2017/7/30 20:06
 * recyclerview 的适配器
 */

public class HomefragmentAdapter extends RecyclerView.Adapter {
    private HomeInfo data;

    /**
     * 网络加载获得到的数据
     *
     * @param info
     */
    public void setData(HomeInfo info) {
        this.data = info;
    }

    // 分析条目的样式：三种
    private final int TYPE_HEAD = 0;
    private final int TYPE_SELLER = 1;
    private final int TYPE_RECOMMEND = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_HEAD:
                holder = new HeadHoler(View.inflate(MyApplication.getContext(), R.layout.item_title, null));
                break;
            case TYPE_SELLER:
                holder = new SellerHoler(View.inflate(MyApplication.getContext(), R.layout.item_seller, null));
                break;
            case TYPE_RECOMMEND:
                holder = new RecommentHoler(View.inflate(MyApplication.getContext(), R.layout.item_division, null));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_HEAD:((HeadHoler)holder).setData(data.head);
                break;
            case TYPE_SELLER:((SellerHoler)holder).setData(data.body.get(position-1).seller);
                break;
            case TYPE_RECOMMEND:((RecommentHoler)holder).setData(data.body.get(position-1));
                break;

        }

    }

    @Override
    public int getItemCount() {
        return data != null ? data.body.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if (position == 0) {
            type = TYPE_HEAD;
        } else {
            type=data.body.get(position).type == 0 ? TYPE_SELLER : TYPE_RECOMMEND;
        }
        return type;
    }

    /**
     * 头容器管理
     */
    class HeadHoler extends RecyclerView.ViewHolder {


        private Head data;
        private SliderLayout sliderLayout;
        private LinearLayout categoryContainer;

        public HeadHoler(View itemView) {
            super(itemView);
            sliderLayout = (SliderLayout) itemView.findViewById(R.id.slider);
            categoryContainer = (LinearLayout) itemView.findViewById(R.id.catetory_container);
        }

        public void setData(Head data) {
            this.data = data;
            sliderLayout.removeAllSliders();//轮播图数据的设置
            if (data != null && data.promotionList.size() > 0) {
                for (Promotion item : data.promotionList) {
                    TextSliderView textSliderView = new TextSliderView(MyApplication.getContext());
                    String replace_pic = item.pic.replace("172.16.0.116", Constant.replace_img_url);
                    textSliderView.image(replace_pic);
                    textSliderView.description(item.info);

                    sliderLayout.addSlider(textSliderView);
                }
            }

            if (data != null && data.categorieList.size() > 0) {//分类视图的设置数据
                categoryContainer.removeAllViews();
                // 0、1  2、3  4、5
                // 0:创建整个条目的布局，将数据设置好后添加到条目中
                // 1:设置数据
                // ……

                View item = null;
                for (int i = 0; i < data.categorieList.size(); i++) {
                    Category category = data.categorieList.get(i);

                    if (i % 2 == 0) {
                        // 每个条目中的第一个元素
                        item = View.inflate(MyApplication.getContext(), R.layout.item_home_head_category, null);
                        String replace_category_pic = category.pic.replace("172.16.0.116", Constant.replace_img_url);
                        Picasso.with(MyApplication.getContext()).load(replace_category_pic).into((ImageView) item.findViewById(R.id.top_iv));
                        ((TextView) item.findViewById(R.id.top_tv)).setText(category.name);

                        categoryContainer.addView(item);
                    }

                    if (i % 2 != 0) {
                        String replace_category_pic = category.pic.replace("172.16.0.116", Constant.replace_img_url);
                        Picasso.with(MyApplication.getContext()).load(replace_category_pic).into((ImageView) item.findViewById(R.id.bottom_iv));
                        ((TextView) item.findViewById(R.id.bottom_tv)).setText(category.name);
                    }
                }
            }


        }
    }

    /**
     * 商家容器管理
     */
    class SellerHoler extends RecyclerView.ViewHolder implements View.OnClickListener {


        private Seller data;
        private TextView tvTitle;
        private TextView tvCount;


        public SellerHoler(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
        }

        public void setData(Seller data) {
            this.data = data;
            tvTitle.setText(data.name);


            // 设置已经购买数量,需要依据商家标识进行区分
            if (data.id == ShoppingCartManager.getInstance().sellerId) {
                Integer num = ShoppingCartManager.getInstance().getTotalNum();
                if (num > 0) {
                    tvCount.setVisibility(View.VISIBLE);
                    tvCount.setText(num.toString());
                } else {
                    tvCount.setVisibility(View.GONE);
                }
            } else {
                tvCount.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getContext(), SellerDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 由于使用了Application的上下文，需要增加该配置信息（错误日志中会提示）
            intent.putExtra("seller_id", data.id);
            intent.putExtra("name", data.name);

            if (ShoppingCartManager.getInstance().sellerId != data.id) {
                // 进入商家时更新购物车商家标识
                ShoppingCartManager.getInstance().sellerId = data.id;
                ShoppingCartManager.getInstance().name = data.name;
                ShoppingCartManager.getInstance().url = data.pic;
                ShoppingCartManager.getInstance().sendPrice = data.sendPrice;
                // 清除掉上一个商家中选择的商品
                ShoppingCartManager.getInstance().clear();
            }

//            // 统计商铺的浏览量
//            MobclickAgent.onEvent(v.getContext(),"seller");
//            HashMap<String, String> map = new HashMap<>();
//            map.put("seller_id",data.id+"");
//            MobclickAgent.onEvent(v.getContext(),"seller",map);


            MyApplication.getContext().startActivity(intent);

        }
    }

    /**
     * 大家都在找的条目管理
     */
    class RecommentHoler extends RecyclerView.ViewHolder {
        private HomeItem data;

        public RecommentHoler(View itemView) {
            super(itemView);
        }

        public void setData(HomeItem data) {
            this.data = data;
        }
    }


}
