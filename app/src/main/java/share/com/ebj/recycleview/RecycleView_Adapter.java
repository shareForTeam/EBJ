package share.com.ebj.recycleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import share.com.ebj.R;
import share.com.ebj.Utils.IconStr_To_List;
import share.com.ebj.jsonStr.SortJson;
import share.com.ebj.listener.OnGetSortJsonListener;

/**
 * Created by Administrator on 2016/9/7.
 */
public class RecycleView_Adapter extends RecyclerView.Adapter<RecycleView_Adapter.MyViewHolder> implements OnGetSortJsonListener {
    private Context context;
    private List<String> iconStr_List = new ArrayList<>();

    public RecycleView_Adapter(Context context){
        this.context = context;
    }


    public void setIconStr_List(List<String> iconStr_List) {
        this.iconStr_List = iconStr_List;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item, parent,false));
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.iv_item.setImageResource(R.mipmap.close);
        ImageLoader.getInstance().displayImage(iconStr_List.get(position),holder.iv_item);
    }

    @Override
    public int getItemCount() {
        return iconStr_List.size();
    }

    @Override
    public void onGetSortJsonListener(SortJson sortJson) {
        for(int i = 0 ; i < sortJson.getList().size() ; i++){
            String goods_icon_Str = sortJson.getList().get(i).getGoods_icon();
            IconStr_To_List iconStr_to_list = new IconStr_To_List();
            List<String> iconList = iconStr_to_list.getIconList(goods_icon_Str);
            String first_icon = iconList.get(0);
            iconStr_List.add(first_icon);
//            no
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_item;
        private TextView tv_name,tv_prize;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_item = (ImageView) itemView.findViewById(R.id.id_sort_rv_item_iv);
            tv_name = (TextView) itemView.findViewById(R.id.id_sort_rv_item_tv_goodsname);
            tv_prize = (TextView) itemView.findViewById(R.id.id_sort_rv_item_goodsprize);
        }
    }


}
