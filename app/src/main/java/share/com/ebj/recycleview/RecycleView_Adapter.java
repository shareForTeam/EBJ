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

/**
 * Created by Administrator on 2016/9/7.
 */
public class RecycleView_Adapter extends RecyclerView.Adapter<RecycleView_Adapter.MyViewHolder>  {
    private Context context;
    private List<String> iconStr_List = new ArrayList<>();
    private List<String> nameStr_List = new ArrayList<>();
    private List<String> prizeStr_List = new ArrayList<>();

    public void setOnRVItemClickListener(OnRVItemClickListener onRVItemClickListener) {
        this.onRVItemClickListener = onRVItemClickListener;
    }

    private OnRVItemClickListener onRVItemClickListener;


    public RecycleView_Adapter(Context context){
        this.context = context;
    }


    public void setIconStr_List(List<String> iconStr_List, List<String> nameStr_List ,List<String> prizeStr_List ) {
        this.iconStr_List = iconStr_List;
        this.nameStr_List = nameStr_List;
        this.prizeStr_List = prizeStr_List;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item, parent,false));
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ImageLoader.getInstance().displayImage(iconStr_List.get(position),holder.iv_item);
        holder.tv_name.setText(nameStr_List.get(position));
        holder.tv_prize.setText(prizeStr_List.get(position));
        holder.iv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onRVItemClickListener != null){
                    onRVItemClickListener.onRVItemClick(view , position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return iconStr_List.size();
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

    public interface OnRVItemClickListener{
        void onRVItemClick(View view, int position);
    }


}
