package share.com.ebj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import share.com.ebj.R;

/**
 * Created by Administrator on 2016/8/30.
 */

/**
 * 給listView添加一个适配器
 */
public class GouWuCheListViewAdapter extends BaseAdapter  {

   private Context context;// 传入的参数
    private LayoutInflater inflater;//可以将Layout文件变为View

    //构造函数
    public GouWuCheListViewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
//TODO:问题LIstView滑动更新组件会重用Item,让数据变得混乱，需要一个javaBean来存储数据
        if (view == null) {
            viewHolder = new ViewHolder();
            // 获取组件布局
            view = inflater.inflate(R.layout.gouwuche_listview_adapter, null);
            viewHolder.imageButton_jia = (ImageButton) view.findViewById(R.id.imagebutton_jia);
            viewHolder.imageButton_jian = (ImageButton) view.findViewById(R.id.imagebutton_jian);
            viewHolder.et_num = (EditText) view.findViewById(R.id.edittext_num);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        /***
         * 点击事件
         */
        //购物车加号的点击事件
        final ViewHolder viewHolder2 = viewHolder;//final一下，转换为全局变脸
        viewHolder.imageButton_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //需要把我们操作的变量传入进来。
                int count =Integer.parseInt(viewHolder2.et_num.getText().toString());
                viewHolder2.et_num.setText((++count)+"");
            }
        });
        //购物车减号的点击事件
        viewHolder.imageButton_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count =Integer.parseInt(viewHolder2.et_num.getText().toString());
               int i= unAddNum(count);
                viewHolder2.et_num.setText(i+"");
            }
        });

        return view;
    }



    //自定义内部ViewHoler
    class ViewHolder {
        RadioButton rb;//购物车是否选择按钮
        ImageView iv; //购物车图片
        TextView textView_title;//服装名字
        ImageButton imageButton_jian;//减号按钮
        ImageButton imageButton_jia;//加号按钮
        EditText et_num;//数量
        TextView textView_chicun;//尺寸显示
        TextView textView_money;//价格
//
    }
//    当数量减少，回调
    public int unAddNum(int  buy_count){
        if (buy_count<=1){
            buy_count=1;
        }else {
            buy_count--;
        }
        return buy_count;
    }
}
