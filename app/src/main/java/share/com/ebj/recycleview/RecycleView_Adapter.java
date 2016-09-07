package share.com.ebj.recycleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import share.com.ebj.R;

/**
 * Created by Administrator on 2016/9/7.
 */
public class RecycleView_Adapter extends RecyclerView.Adapter<RecycleView_Adapter.MyViewHolder> {
    private Context context;
    private List<Bitmap> bitmapList;

    public RecycleView_Adapter(Context context){
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item, parent,false));
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.iv_item.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return 16;
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
