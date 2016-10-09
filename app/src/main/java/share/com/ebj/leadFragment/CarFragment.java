package share.com.ebj.leadFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import share.com.ebj.Activity.PayClickActivity;
import share.com.ebj.JavaBean.GoodsBean_For_Car_ListView;
import share.com.ebj.R;
import share.com.ebj.SingleUser.UserSingleton;
import share.com.ebj.Utils.DBOperation;
import share.com.ebj.Utils.StrManager;
import share.com.ebj.adapter.GouWuCheListViewAdapter;
import share.com.ebj.jsonStr.ProductJson;

/**
 * Created by Administrator on 2016/9/8.
 */
public class CarFragment extends Fragment implements View.OnClickListener ,GouWuCheListViewAdapter.OnItemCheckedChangeListener,GouWuCheListViewAdapter.OnCountChangeListener {
    private String TAG = "crazyK";
    private ImageView gouwuche_back_img,iv_delete;
    private TextView tv_total_prize;
    private Button gouwucge_pay_button;
    private ListView gouwuche_listview;
    private CheckBox check_all_btn;
    private GouWuCheListViewAdapter gouWuCheListViewAdapter;
    private int user_id;
    private ArrayList<GoodsBean_For_Car_ListView> listGoods;
    private String new_goods_id_del;//删除以后的新goods_id

    // TODO: 2016/9/16 多选按钮多次点击，价钱会不对。。

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_fragment, container, false);
        initView(view);
        initListener();


        gouWuCheListViewAdapter = new GouWuCheListViewAdapter(getActivity(),this);
        gouwuche_listview.setAdapter(gouWuCheListViewAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        isLogin();
    }

    public void initView(View view){
        gouwuche_back_img = (ImageView) view.findViewById(R.id.gouwuche_back_img);//返回按钮
        gouwucge_pay_button = (Button) view.findViewById(R.id.gouwucge_pay_button);
        gouwuche_listview = (ListView) view.findViewById(R.id.gouwuche_listview);
        check_all_btn = (CheckBox) view.findViewById(R.id.check_button);
        tv_total_prize = (TextView) view.findViewById(R.id.total_prize);
        iv_delete = (ImageView) view.findViewById(R.id.car_iv_delete);

    }

    public void initListener(){
        gouwucge_pay_button.setOnClickListener(this);
        gouwuche_back_img.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        check_all_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for(int i = 0 ; i<listGoods.size() ; i++){
                    listGoods.get(i).setIsCheck(b);
                }

                gouWuCheListViewAdapter.setlistGoodsBean(listGoods);
                int totalPrize = getTotalPrize();
                tv_total_prize.setText(totalPrize+"");
            }
        });
    }

    /**判断是否登录，若登录设置adapter、listview*/
    public void isLogin(){
        SharedPreferences login_SP = getActivity().getSharedPreferences("user_id", Context.MODE_PRIVATE);
        user_id = login_SP.getInt("user_id", -1);
        if(user_id != -1){
            /**已登录*/
            UserSingleton userSingleton = UserSingleton.getInstance();
            String goods_ids = userSingleton.getGoods_id();
            if(goods_ids != null){
                StrManager strManager = new StrManager();
                List<String> goods_list = strManager.getIconList(goods_ids);

                /**访问服务器获得ProductJson(商品信息),并对其进一步封装，添加isCheck、et_count、position属性*/

                listGoods = new ArrayList<>();
                for(int i = 0 ; i <goods_list.size() ; i++){
                    String goods_id = goods_list.get(i);
                    getGoodsInfo(Integer.parseInt(goods_id),listGoods);

                }
            }


        }else {
            /**不应该出现没有登录就进入这个页面的逻辑，如果发生打印错误日志*/
        }
    }

    /**获取商品详情*/
    public void getGoodsInfo(final int goods_id , final ArrayList<GoodsBean_For_Car_ListView> listGoods){
        RequestParams requestParams = new RequestParams("http://172.18.4.18:8080/EBJ_Project/goods_detail.do?");
        requestParams.addParameter("type", "android");
        requestParams.addParameter("query", "detail");
        requestParams.addParameter("goods_id", goods_id);

        x.http().request(HttpMethod.POST, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result != null){
                    Gson gson = new Gson();
                    ProductJson productJson = gson.fromJson(result, ProductJson.class);

                    /**将商品信息进一步封装成一个javaBean，额外加上isCheck、et_Count、position属性*/
                    int size = productJson.getList().size();
                    if(size == 1){
                        ProductJson.ListBean goods = productJson.getList().get(0);
                        String goods_name = goods.getGoods_name();
                        String goods_icon = new StrManager().getIconList(goods.getGoods_icon()).get(0);
                        String goods_size = goods.getGoods_size();
                        String goods_prize = goods.getGoods_price();

                        GoodsBean_For_Car_ListView goodsBean_for_car_listView = new GoodsBean_For_Car_ListView();
                        goodsBean_for_car_listView.setGoods_id(goods_id);
                        goodsBean_for_car_listView.setName(goods_name);
                        goodsBean_for_car_listView.setIcon(goods_icon);
                        goodsBean_for_car_listView.setSize(goods_size);
                        goodsBean_for_car_listView.setPrize(goods_prize);

                        listGoods.add(goodsBean_for_car_listView);
                        gouWuCheListViewAdapter.setlistGoodsBean(listGoods);
                    }else {
                        Log.i(TAG, "通过商品id返回的数据不唯一 ");
                    }

                }else {
                    Log.i(TAG, "返回的商品详情result为空");
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /***返回*/
            case R.id.gouwuche_back_img:
                getActivity().finish();
                break;
            /**支付*/
            case R.id.gouwucge_pay_button:
                Intent intent_CarToPay = new Intent(getActivity(), PayClickActivity.class);
                startActivity(intent_CarToPay);
                break;
            /**删除*/
            case R.id.car_iv_delete:
                for(int i = 0 ; i < this.listGoods.size() ; i++){
                    GoodsBean_For_Car_ListView goodsBean_for_car_listView = listGoods.get(i);
                    boolean isCheck = goodsBean_for_car_listView.getIsCheck();
                    if(isCheck){
//                        this.listGoods.remove(goodsBean_for_car_listView);
                        int user_id = getActivity().getSharedPreferences("user_id", Context.MODE_PRIVATE).getInt("user_id", -1);
                        if(user_id != -1){
                            int goods_id = goodsBean_for_car_listView.getGoods_id();

                            UserSingleton userSingleton = UserSingleton.getInstance();
                            String user_goods_id = userSingleton.getGoods_id();
                            StrManager strManager = new StrManager();
                            this.new_goods_id_del = strManager.deleteGoods_id(user_goods_id, "" + goods_id);
                            /**更新 删除单例goods_id*/
                            // TODO: 2016/9/16 此处由于逻辑原因，必须先行更新Usersingleton。如果放在服务器成功更新后再更新，由于服务器是异步操作，会出现在本地还没更新的时候就进入下一个for循环，导致服务器只会删除最后一个goods_id
                            UserSingleton.getInstance().setGoods_id(this.new_goods_id_del);
                            if (this.new_goods_id_del == null) {
                                return;
                            } else {
                                /**更新服务器数据库
                                 * 若成功在onSuccess中更新本地及单例
                                 * 删除listview中选中的item
                                 * */
                                Log.i(TAG, "goods_id1: "+goods_id);
                                Log.i(TAG, "new_goods_id_del1: "+new_goods_id_del);
                                updateUserGoods_iv_selected(user_id, this.new_goods_id_del,goodsBean_for_car_listView);
                            }

                        }else {
                            Log.i(TAG, "CarFragment --> onClick() --> R.id.car_iv_delete --> 错误：用户未登录还能购物车！");
                        }
                    }
                }
                this.gouWuCheListViewAdapter.setlistGoodsBean(this.listGoods);

                break;



        }
    }

    /**
     * 向服务器发送请求，购物车 添加商品id ,操作成功或失败toast
     * 实质为：用new_goods_id 更新数据库
     */
    public void updateUserGoods_iv_selected(final int user_id, final String new_goods_id, final GoodsBean_For_Car_ListView goodsBean_for_car_listView) {
        Log.i(TAG, "new_goods_id2: "+new_goods_id);

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

                        /**删除car_listview中选中的部分*/
                        listGoods.remove(goodsBean_for_car_listView);
                        gouWuCheListViewAdapter.setlistGoodsBean(listGoods);

                    } else {
                        Toast.makeText(getActivity(), "本地数据库更新异常", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: "+isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

        });

    }


    /**以下两个接口是将listview中的绝对位置等信息传过来，然后更新GoodsBean_For_Car_ListView*/
    @Override
    public void onCountChangeListener(int count, int position) {
        GoodsBean_For_Car_ListView goodsBean_for_car_listView = this.listGoods.get(position);
        goodsBean_for_car_listView.setEt_count(count);
        this.gouWuCheListViewAdapter.setlistGoodsBean(listGoods);

        int totalPrize = getTotalPrize();
        tv_total_prize.setText(totalPrize+"");
    }

    @Override
    public void onItemCheckedChangeListener(boolean isCheck, int position) {
        GoodsBean_For_Car_ListView goodsBean_for_car_listView = this.listGoods.get(position);
        goodsBean_for_car_listView.setIsCheck(isCheck);
        this.gouWuCheListViewAdapter.setlistGoodsBean(listGoods);

        int totalPrize = getTotalPrize();
        tv_total_prize.setText(totalPrize+"");
    }

    public int getTotalPrize(){
        int totalPrize = 0 ;
        for(int i = 0 ; i< this.listGoods.size() ; i++){
            GoodsBean_For_Car_ListView goodsBean_for_car_listView = this.listGoods.get(i);
            boolean isCheck = goodsBean_for_car_listView.getIsCheck();
            if(isCheck){
                totalPrize += Integer.parseInt(goodsBean_for_car_listView.getPrize()) * goodsBean_for_car_listView.getEt_count();
            }
        }
        return totalPrize;
    }
}