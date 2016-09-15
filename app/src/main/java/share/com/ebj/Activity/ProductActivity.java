package share.com.ebj.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import share.com.ebj.R;
import share.com.ebj.SingleUser.UserSingleton;
import share.com.ebj.Utils.DBOperation;
import share.com.ebj.Utils.StrManager;
import share.com.ebj.adapter.Goods_VP_Adapter;
import share.com.ebj.init.InitActivity;
import share.com.ebj.jsonStr.ProductJson;
import share.com.ebj.sqlite.User_Info;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "crazyK";
    private final int REQUEST_CODE = 0;
    //从分类界面传来的goods_id
    private int goods_id;

    private ViewPager vp_product;
    private TextView tv_prize, tv_washing, tv_name;
    private ProductJson productJson;
    private String goods_name;
    private String goods_price;
    private String goods_washing;
    private List<String> iconStr_List;
    private List<String> description_List;
    //动态添加商品图片
    private LinearLayout ll_goods_pic;
    private ImageView imageView_car;
    //动态添加商品信息
    private LinearLayout id_product_ll_productinfo;
    //商品信息图片下方的客服、购物车、收藏
    private LinearLayout ll_shopCar;
    private ImageView iv_shopCar;

    private int loginSP_user_id;
    private String new_goods_id_del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();

        goods_id = intent.getIntExtra("goods_id", -1);

        initView();
        initListener();

        initSearch(goods_id);

        /**判断是否登录,登录状态下判断是否已经收藏该商品，设置iv_shopCar的选中状态*/
        isLogin("" + goods_id);


    }

    public void initView() {
        vp_product = (ViewPager) findViewById(R.id.product_viewpager);
        tv_prize = (TextView) findViewById(R.id.id_product_prize);
        tv_washing = (TextView) findViewById(R.id.id_product_washing);
        id_product_ll_productinfo = (LinearLayout) findViewById(R.id.id_product_ll_productinfo);
        tv_name = (TextView) findViewById(R.id.product_name);
        ll_goods_pic = (LinearLayout) findViewById(R.id.product_ll_goods_pic);
        ll_shopCar = (LinearLayout) findViewById(R.id.product_ll_shopcar);
        iv_shopCar = (ImageView) findViewById(R.id.product_iv_shopcar);
        imageView_car = (ImageView) findViewById(R.id.product_iv_shopcar);
    }

    public void initListener() {
        ll_shopCar.setOnClickListener(this);
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
//                Log.i(TAG, "onSuccess: " + result);
                iconStr_List = new ArrayList<String>();
                description_List = new ArrayList<String>();

                Gson gson = new Gson();
                productJson = gson.fromJson(result, ProductJson.class);
                String goods_icon_Str = productJson.getList().get(0).getGoods_icon();
                goods_name = productJson.getList().get(0).getGoods_name();
                goods_price = productJson.getList().get(0).getGoods_price();
                goods_washing = productJson.getList().get(0).getGoods_washing();
                String goods_description = productJson.getList().get(0).getGoods_description();


                StrManager str_manager = new StrManager();
                iconStr_List = str_manager.getIconList(goods_icon_Str);
                description_List = str_manager.getIconList(goods_description);

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
                for (int i = 0; i < iconStr_List.size(); i++) {
                    ImageView imageView = new ImageView(ProductActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 7, 0, 7);
                    imageView.setLayoutParams(params);
                    imageLoader.displayImage(iconStr_List.get(i), imageView);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /**购物车*/
            case R.id.product_ll_shopcar:
                imageView_car.setImageResource(R.drawable.shopcar);

                SharedPreferences loginSP = getSharedPreferences("user_id", MODE_PRIVATE);

                loginSP_user_id = loginSP.getInt("user_id", -1);
                if (loginSP_user_id != -1) {
                    if (iv_shopCar.isSelected()) {

                        /**删除UserSingleton中对应的goods_id，删除本地数据库user表的相应goods_id，删除服务器上数据*/
                        UserSingleton userSingleton = UserSingleton.getInstance();
                        String user_goods_id = userSingleton.getGoods_id();
                        StrManager strManager = new StrManager();
                        this.new_goods_id_del = strManager.deleteGoods_id(user_goods_id, "" + this.goods_id);
                        if (this.new_goods_id_del == null) {
                            return;
                        } else {
                            /**更新服务器数据库
                             * 若成功在onSuccess中更新本地及单例
                             * */
                            updateUserGoods_iv_selected(this.loginSP_user_id, this.new_goods_id_del);
                        }

                    } else {
                        /**iv_shopcar未被选中时，向服务器发送请求，购物车 添加商品id ,操作成功或失败toast*/

                        UserSingleton userSingleton = UserSingleton.getInstance();
//                        String new_goods_id = userSingleton.addGoods_id("" + this.goods_id);
                        String user_goods_id = userSingleton.getGoods_id();
                        StrManager strManager = new StrManager();
                        String new_goods_id_add = strManager.addGoods_id(user_goods_id, "" + this.goods_id);
                        /**更新web数据库中的数据*/
                        updateUserGoods_iv_unSelected(loginSP_user_id, new_goods_id_add);
                    }


                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(null)
                            .setMessage("您还未登录，请问是否登录？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent_ProductToLogin = new Intent(ProductActivity.this, LoginActivity.class);
                                    startActivity(intent_ProductToLogin);
                                }
                            })
                            .setNegativeButton("否", null)
                            .create().show();
                }
                break;
        }
    }


    /**
     * 向服务器发送请求，购物车 添加商品id ,操作成功或失败toast
     * 实质为：用new_goods_id 更新数据库
     */
    public void updateUserGoods_iv_selected(final int user_id, final String new_goods_id) {

        RequestParams params = new RequestParams("http://172.18.4.18:8080/EBJ_Project/user_goods.do");
        params.addParameter("type", "android");
        params.addParameter("query", "user_goods");
        params.addParameter("user_id", user_id);
        params.addParameter("goods_id", new_goods_id);
        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("购物车更新成功")) {
                    /**更新删除本地数据库*/
                    DBOperation dbOperation = new DBOperation();
                    boolean isSuccess = dbOperation.updateGoods_id_To_ShopCar(user_id, new_goods_id);
                    if (isSuccess) {

                    } else {
                        Toast.makeText(ProductActivity.this, "本地数据库更新异常", Toast.LENGTH_SHORT).show();
                    }
                    /**更新 删除单例goods_id*/
                    UserSingleton.getInstance().setGoods_id(new_goods_id);
                    iv_shopCar.setSelected(false);
                    ll_shopCar.setSelected(false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

        });

    }

    public void updateUserGoods_iv_unSelected(final int user_id, final String new_goods_id) {
        RequestParams params = new RequestParams("http://172.18.4.18:8080/EBJ_Project/user_goods.do");
        params.addParameter("type", "android");
        params.addParameter("query", "user_goods");
        params.addParameter("user_id", user_id);
        params.addParameter("goods_id", new_goods_id);

        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("购物车更新成功")) {
                    /**更新删除本地数据库*/
                    DBOperation dbOperation = new DBOperation();
                    boolean isSuccess = dbOperation.updateGoods_id_To_ShopCar(user_id, new_goods_id);
                    if (isSuccess) {

                    } else {
                        Toast.makeText(ProductActivity.this, "本地数据库更新异常", Toast.LENGTH_SHORT).show();
                    }
                    /**更新 删除单例goods_id*/
                    UserSingleton.getInstance().setGoods_id(new_goods_id);
                    iv_shopCar.setSelected(true);
                    ll_shopCar.setSelected(true);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

        });
    }

    /**
     * 判断是否登录,登录状态下判断是否已经收藏该商品，设置iv_shopCar的选中状态
     */
    public void isLogin(String goods_id) {
        SharedPreferences loginSP = getSharedPreferences("user_id", MODE_PRIVATE);
        int loginSP_user_id = loginSP.getInt("user_id", -1);
        if (loginSP_user_id == -1) {
            iv_shopCar.setSelected(false);
            ll_shopCar.setSelected(false);
        } else {
            /**登录状态下判断是否已经收藏该商品，设置iv_shopCar的选中状态*/
            UserSingleton userSingleton = UserSingleton.getInstance();
            String user_goods_id = userSingleton.getGoods_id();
            if (user_goods_id != null) {
                boolean isExit = user_goods_id.contains(goods_id);
                if (isExit) {
                    iv_shopCar.setSelected(true);
                    ll_shopCar.setSelected(true);
                } else {
                    iv_shopCar.setSelected(false);
                    ll_shopCar.setSelected(false);
                }
            } else {
                iv_shopCar.setSelected(false);
                ll_shopCar.setSelected(false);
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isLogin("" + goods_id);
    }
}
