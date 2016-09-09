package share.com.ebj.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import share.com.ebj.R;
import share.com.ebj.Utils.IconStr_To_List;
import share.com.ebj.adapter.Goods_VP_Adapter;
import share.com.ebj.jsonStr.ProductJson;

public class ProductActivity extends AppCompatActivity {
    private String TAG = "crazyK";
    private ViewPager vp_product;
    private TextView tv_prize, tv_washing,tv_name;
    private ProductJson productJson;
    private String goods_name;
    private String goods_price;
    private String goods_washing;
    private List<String> iconStr_List;
    private List<String> description_List;
    //动态添加商品图片
    private LinearLayout ll_goods_pic;
    //动态添加商品信息
    private LinearLayout id_product_ll_productinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        int goods_id = intent.getIntExtra("goods_id", -1);
        Log.i(TAG, "goods_id: " + goods_id);


        initView();


        initSearch(goods_id);


    }

    public void initView() {
        vp_product = (ViewPager) findViewById(R.id.product_viewpager);
        tv_prize = (TextView) findViewById(R.id.id_product_prize);
        tv_washing = (TextView) findViewById(R.id.id_product_washing);
        id_product_ll_productinfo = (LinearLayout) findViewById(R.id.id_product_ll_productinfo);
        tv_name = (TextView) findViewById(R.id.product_name);
        ll_goods_pic = (LinearLayout) findViewById(R.id.product_ll_goods_pic);
    }

    public void initSearch(int goods_id) {
        if (goods_id == -1) {
            return;
        }

        RequestParams requestParams = new RequestParams("http://172.18.4.18:8080/EBJ_Project/goods_detail.do?");
        requestParams.addParameter("type", "android");
        requestParams.addParameter("query", "detail");
        requestParams.addParameter("goods_id", goods_id);
        x.http().request(HttpMethod.POST, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: " + result);
                iconStr_List = new ArrayList<String>();
                description_List = new ArrayList<String>();

                Gson gson = new Gson();
                productJson = gson.fromJson(result, ProductJson.class);
//                for(int i = 0; i < productJson.getList().size() ; i++){
                String goods_icon_Str = productJson.getList().get(0).getGoods_icon();
                goods_name = productJson.getList().get(0).getGoods_name();
                goods_price = productJson.getList().get(0).getGoods_price();
                goods_washing = productJson.getList().get(0).getGoods_washing();
                String goods_description = productJson.getList().get(0).getGoods_description();


                IconStr_To_List iconStr_to_list = new IconStr_To_List();
                iconStr_List = iconStr_to_list.getIconList(goods_icon_Str);
                description_List = iconStr_to_list.getIconList(goods_description);
//                }
//                product_rv_adapter.setIconStr_List(description_List);

                /**设置商品washing信息*/
                tv_washing.setText(goods_washing);

                /**设置价格*/
                tv_prize.setText(goods_price);

                /**设置名字*/
                tv_name.setText(goods_name);

                /**设置商品viewpager*/
                Goods_VP_Adapter goods_vp_adapter = new Goods_VP_Adapter();
                goods_vp_adapter.setIconStr_List(iconStr_List);
                vp_product.setOffscreenPageLimit(4);
                vp_product.setAdapter(goods_vp_adapter);

                /**动态添加商品信息的消息
                 * */
                for (int i = 0; i < description_List.size(); i += 2) {
                    String it1 = null;
                    if (i < description_List.size()) {
                        it1 = description_List.get(i);
                    }

                    String it2 = null;
                    if (i + 1 < description_List.size()) {
                        it2 = description_List.get(i + 1);
                    }
                    id_product_ll_productinfo.addView(createItemView(it1, it2));
                }

                /**动态添加商品图片*/
                ImageLoader imageLoader = ImageLoader.getInstance();
                for(int i = 0 ; i < iconStr_List.size() ; i++){
                    ImageView imageView = new ImageView(ProductActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0,7,0,7);
                    imageView.setLayoutParams(params);
                    imageLoader.displayImage(iconStr_List.get(i),imageView);
                    ll_goods_pic.addView(imageView);
                }

            }


            private View createItemView(String... items) {
                LinearLayout itemLin = new LinearLayout(ProductActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                itemLin.setLayoutParams(layoutParams);
                itemLin.setOrientation(LinearLayout.HORIZONTAL);
                for (String s : items) {
                    TextView tv = new TextView(ProductActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    int margin = 4;
                    params.setMargins(margin, margin, margin, margin);
                    tv.setSingleLine();
                    if (s != null) {
                        tv.setText(s);
                    } else {
                        tv.setText("");
                        tv.setVisibility(View.INVISIBLE);
                    }
                    tv.setLayoutParams(params);
                    tv.setPadding(33, 25, 33, 25);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setBackgroundColor(getResources().getColor(R.color.product_info_background));
                    tv.setTextColor(getResources().getColor(R.color.product_info_text));
                    itemLin.addView(tv);

                }
                return itemLin;
            }


            /**isOnCallback :true:本地onSuceess中逻辑错误
             * false：远程服务器错误
             * */
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: " + isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
}
