package share.com.ebj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/27.
 */

public class GouWuChe extends AppCompatActivity {
    private ImageView gouwuche_back_img;
    private Button gouwucge_pay_button;
    private ListView gouwuche_listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gouwuche);
        init();
        gouwuche_listview.setAdapter(new MyAdapder(GouWuChe.this));
    }

    /**
     * 找到我们关心的控件,初始化
     */

    private void init() {
        gouwuche_back_img = (ImageView) findViewById(R.id.gouwuche_back_img);
        gouwucge_pay_button = (Button) findViewById(R.id.gouwucge_pay_button);
        gouwuche_listview = (ListView) findViewById(R.id.gouwuche_listview);

    }


}

/**
 * 給listView添加一个适配器
 */
class MyAdapder extends BaseAdapter {
    GouWuChe context;

    public MyAdapder(GouWuChe context) {
        this.context = context;
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
        ViewHolder viewHolder=null;

        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.gouwuche_listview_adapter, null);

                view.setTag(viewHolder);
        }else{

            viewHolder= (ViewHolder) view.getTag();
        }

        return view;
    }

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
}
