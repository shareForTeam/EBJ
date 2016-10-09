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
import android.widget.CompoundButton;
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

import java.util.ArrayList;
import java.util.List;

import share.com.ebj.JavaBean.GoodsBean_For_Car_ListView;
import share.com.ebj.R;
import share.com.ebj.Utils.StrManager;
import share.com.ebj.jsonStr.ProductJson;
import share.com.ebj.leadFragment.CarFragment;

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
    private OnItemCheckedChangeListener onItemCheckedChangeListener;
    private OnCountChangeListener onCountChangeListener;

//    private String goods_name;
//    private String goods_icon;
//    private String goods_size;
//    private String goods_prize;


    private ArrayList<GoodsBean_For_Car_ListView> listGoods;

    //构造函数
    public GouWuCheListViewAdapter(Context context, CarFragment carFragment) {
        this.context = context;
        this.onItemCheckedChangeListener = carFragment;
        this.onCountChangeListener = carFragment;
        inflater = LayoutInflater.from(context);

    }

    public void setlistGoodsBean(ArrayList<GoodsBean_For_Car_ListView> listGoods) {
        this.listGoods = listGoods;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (listGoods != null) {
            return listGoods.size();
        } else {
            return 0;
        }

    }

    @Override
    public Object getItem(int i) {
        if (listGoods != null) {
            return listGoods.get(i);
        } else {
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
        if (view == null) {
            viewHolder = new ViewHolder();
            // 获取组件布局
            view = inflater.inflate(R.layout.gouwuche_listview_adapter, viewGroup, false);
            initView(viewHolder, view);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        /**通过listGoods设置相应界面显示*/
        setShopCarView(viewHolder, position);


        return view;
    }


    public void initView(ViewHolder viewHolder, View view) {
        viewHolder.imageButton_jia = (ImageButton) view.findViewById(R.id.imagebutton_jia);
        viewHolder.imageButton_jian = (ImageButton) view.findViewById(R.id.imagebutton_jian);
        viewHolder.et_num = (EditText) view.findViewById(R.id.edittext_num);
        viewHolder.item_checkBox = (CheckBox) view.findViewById(R.id.item_check_button);
        viewHolder.textView_title = (TextView) view.findViewById(R.id.adapter_titel);
        viewHolder.iv = (ImageView) view.findViewById(R.id.adapter_image);
        viewHolder.iv_chicun = (ImageView) view.findViewById(R.id.adapter_chicun);
        viewHolder.textView_money = (TextView) view.findViewById(R.id.adapter_money);
    }

    public void initListener(final ViewHolder viewHolder, final int position) {
        viewHolder.imageButton_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(viewHolder.et_num.getText().toString());
//                viewHolder.et_num.setText(++count+"");
                onCountChangeListener.onCountChangeListener(++count, position);
            }
        });
        viewHolder.imageButton_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(viewHolder.et_num.getText().toString());
                int i = unAddNum(count);
//                viewHolder.et_num.setText(i + "");
                onCountChangeListener.onCountChangeListener(i, position);
            }
        });
        viewHolder.item_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (viewHolder.item_checkBox.isChecked()) {
                    onItemCheckedChangeListener.onItemCheckedChangeListener(true, position);
                } else {
                    onItemCheckedChangeListener.onItemCheckedChangeListener(false, position);
                }

            }
        });
    }

    /**
     * 通过listGoods设置相应界面显示
     */
    public void setShopCarView(ViewHolder viewHolder, int position) {

        // TODO: 2016/9/16 让蛟神讲下initListener为何放这里就对了。。
        initListener(viewHolder, position);

        GoodsBean_For_Car_ListView goodsBean_for_car_listView = this.listGoods.get(position);
        String name = goodsBean_for_car_listView.getName();
        String icon = goodsBean_for_car_listView.getIcon();
        String size = goodsBean_for_car_listView.getSize();
        String prize = goodsBean_for_car_listView.getPrize();
        int count = goodsBean_for_car_listView.getEt_count();
        boolean isCheck = goodsBean_for_car_listView.getIsCheck();

//        /**设置goodsBean_for_car_listView中的position绝对属性*/
//        goodsBean_for_car_listView.setPosition(position);
        /**设置名字*/
        viewHolder.textView_title.setText(name);
        /**设置图片*/
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(icon, viewHolder.iv);
        /**设置尺寸大小*/
        if (size.equals("S")) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.adapter_chicun_s);
            viewHolder.iv_chicun.setImageBitmap(bitmap);
        } else if (size.equals("M")) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.adapter_chicun_m);
            viewHolder.iv_chicun.setImageBitmap(bitmap);
        } else if (size.equals("L")) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.adater_chicun_l);
            viewHolder.iv_chicun.setImageBitmap(bitmap);
        }
        /**设置价格*/
        viewHolder.textView_money.setText(prize);
        /**设置商品个数*/
        viewHolder.et_num.setText(count + "");
        /**设置isCheck*/
        viewHolder.item_checkBox.setChecked(isCheck);



    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckedChangeListener(boolean isCheck, int position);
    }

    public interface OnCountChangeListener {
        void onCountChangeListener(int count, int position);
    }


//    /**获取商品详情*/
//    public void getGoodsInfo(int goods_id , final ViewHolder viewHolder ){
//        RequestParams requestParams = new RequestParams("http://172.18.4.18:8080/EBJ_Project/goods_detail.do?");
//        requestParams.addParameter("type", "android");
//        requestParams.addParameter("query", "detail");
//        requestParams.addParameter("goods_id", goods_id);
//
//        x.http().request(HttpMethod.POST, requestParams, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                if(result != null){
//                    Gson gson = new Gson();
//                    ProductJson productJson = gson.fromJson(result, ProductJson.class);
//                    int size = productJson.getList().size();
//                    if(size == 1){
//                        ProductJson.ListBean goods = productJson.getList().get(0);
//                        goods_name = goods.getGoods_name();
//                        goods_icon = new StrManager().getIconList(goods.getGoods_icon()).get(0);
//                        goods_size = goods.getGoods_size();
//                        goods_prize = goods.getGoods_price();
//                        /**设置名字*/
//                        viewHolder.textView_title.setText(goods_name);
//                        /**设置图片*/
//                        ImageLoader imageLoader = ImageLoader.getInstance();
//                        imageLoader.displayImage(goods_icon,viewHolder.iv);
//                        /**设置尺寸大小*/
//                        if(goods_size.equals("S")){
//                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.adapter_chicun_s);
//                            viewHolder.iv_chicun.setImageBitmap(bitmap);
//                        }else if(goods_size.equals("M")){
//                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.adapter_chicun_m);
//                            viewHolder.iv_chicun.setImageBitmap(bitmap);
//                        }else if(goods_size.equals("L")){
//                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.adater_chicun_l);
//                            viewHolder.iv_chicun.setImageBitmap(bitmap);
//                        }
//                        /**设置价格*/
//                        viewHolder.textView_money.setText(goods_prize);
//                    }else {
//                        Log.i(TAG, "通过商品id返回的数据不唯一 ");
//                    }
//                }else {
//                    Log.i(TAG, "返回的商品详情result为空");
//                }
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//
//    }

    //自定义内部ViewHoler
    class ViewHolder {
        CheckBox item_checkBox;//购物车是否选择按钮
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
