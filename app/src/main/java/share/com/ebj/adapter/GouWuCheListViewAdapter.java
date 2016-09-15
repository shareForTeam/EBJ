package share.com.ebj.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import share.com.ebj.R;
import share.com.ebj.Utils.StrManager;
import share.com.ebj.jsonStr.ProductJson;

/**
 * Created by Administrator on 2016/8/30.
 */

/**
 * 給listView添加一个适配器
 */
public class GouWuCheListViewAdapter extends BaseAdapter {
    private String TAG = "crazyK";
    private CheckBox check_all_btnx;

    private Context context;// 传入的参数
    private LayoutInflater inflater;//可以将Layout文件变为View

    private String goods_name;
    private String goods_icon;
    private String goods_size;
    private String goods_prize;


    private List<String> goods_list;

    //构造函数
    public GouWuCheListViewAdapter(Context context,CheckBox checkBox) {
        this.context = context;
        this.check_all_btnx = checkBox;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        if(goods_list != null){
            return goods_list.size();
        }else {
            return 0;
        }

    }

    @Override
    public Object getItem(int i) {
        if(goods_list != null){
            return goods_list.get(i);
        }else {
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
//TODO:问题LIstView滑动更新组件会重用Item,让数据变得混乱，需要一个javaBean来存储数据
        if (view == null) {
            viewHolder = new ViewHolder();
            // 获取组件布局
            view = inflater.inflate(R.layout.gouwuche_listview_adapter, null);
            initView(viewHolder,view);
            initListener(viewHolder,position,view);

            /**全选*/
            if(check_all_btnx.isChecked()){
                viewHolder.checkBox.setChecked(true);
            }else {
                viewHolder.checkBox.setChecked(false);
            }
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        /**全选*/
        if(check_all_btnx.isChecked()){
            viewHolder.checkBox.setChecked(true);
        }else {
            viewHolder.checkBox.setChecked(false);
        }

        /**获取商品详情*/
        getGoodsInfo(Integer.parseInt(this.goods_list.get(position)),viewHolder);


//        /***
//         * 点击事件
//         */
//        //购物车加号的点击事件
//        final ViewHolder viewHolder2 = viewHolder;//final一下，转换为全局变脸
//        viewHolder.imageButton_jia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //需要把我们操作的变量传入进来。
//                int count = Integer.parseInt(viewHolder2.et_num.getText().toString());
//                viewHolder2.et_num.setText((++count) + "");
//            }
//        });
//        //购物车减号的点击事件
//        viewHolder.imageButton_jian.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int count = Integer.parseInt(viewHolder2.et_num.getText().toString());
//                int i = unAddNum(count);
//                viewHolder2.et_num.setText(i + "");
//            }
//        });

        return view;
    }

    public void initView(ViewHolder viewHolder ,View view){
        viewHolder.imageButton_jia = (ImageButton) view.findViewById(R.id.imagebutton_jia);
        viewHolder.imageButton_jian = (ImageButton) view.findViewById(R.id.imagebutton_jian);
        viewHolder.et_num = (EditText) view.findViewById(R.id.edittext_num);
        viewHolder.checkBox = (CheckBox) view.findViewById(R.id.check_button);
        viewHolder.textView_title = (TextView) view.findViewById(R.id.adapter_titel);
        viewHolder.iv = (ImageView) view.findViewById(R.id.adapter_image);
        viewHolder.iv_chicun = (ImageView) view.findViewById(R.id.adapter_chicun);
        viewHolder.textView_money = (TextView) view.findViewById(R.id.adapter_money);
    }

    public void initListener(final ViewHolder viewHolder , int position , View view){
        viewHolder.imageButton_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(viewHolder.et_num.getText().toString());
                viewHolder.et_num.setText(++count+"");
            }
        });
        viewHolder.imageButton_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(viewHolder.et_num.getText().toString());
                int i = unAddNum(count);
                viewHolder.et_num.setText(i + "");
            }
        });
    }


    public void setGoodsList(List<String> goods_list){
        this.goods_list = goods_list;
        notifyDataSetChanged();
    }

    /**获取商品详情*/
    public void getGoodsInfo(int goods_id , final ViewHolder viewHolder ){
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
                    int size = productJson.getList().size();
                    if(size == 1){
                        ProductJson.ListBean goods = productJson.getList().get(0);
                        goods_name = goods.getGoods_name();
                        goods_icon = new StrManager().getIconList(goods.getGoods_icon()).get(0);
                        goods_size = goods.getGoods_size();
                        goods_prize = goods.getGoods_price();
                        /**设置名字*/
                        viewHolder.textView_title.setText(goods_name);
                        /**设置图片*/
                        ImageLoader imageLoader = ImageLoader.getInstance();
                        imageLoader.displayImage(goods_icon,viewHolder.iv);
                        /**设置尺寸大小*/
                        if(goods_size.equals("S")){
                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.adapter_chicun_s);
                            viewHolder.iv_chicun.setImageBitmap(bitmap);
                        }else if(goods_size.equals("M")){
                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.adapter_chicun_m);
                            viewHolder.iv_chicun.setImageBitmap(bitmap);
                        }else if(goods_size.equals("L")){
                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.adater_chicun_l);
                            viewHolder.iv_chicun.setImageBitmap(bitmap);
                        }
                        /**设置价格*/
                        viewHolder.textView_money.setText(goods_prize);
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

    //自定义内部ViewHoler
    class ViewHolder {
        CheckBox checkBox;//购物车是否选择按钮
        ImageView iv; //购物车图片
        TextView textView_title;//服装名字
        ImageButton imageButton_jian;//减号按钮
        ImageButton imageButton_jia;//加号按钮
        EditText et_num;//数量
        ImageView iv_chicun;//尺寸显示
        TextView textView_money;//价格
//
    }

    //    当数量减少，回调
    public int unAddNum(int buy_count) {
        if (buy_count <= 1) {
            buy_count = 1;
        } else {
            buy_count--;
        }
        return buy_count;
    }
}
