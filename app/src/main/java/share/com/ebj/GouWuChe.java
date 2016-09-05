package share.com.ebj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/8/27.
 */

public class GouWuChe extends AppCompatActivity implements View.OnClickListener {
    private ImageView gouwuche_back_img;
    private Button gouwucge_pay_button;
    private ListView gouwuche_listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gouwuche);
        init();
        gouwuche_listview.setAdapter(new GouWuCheListViewAdapter(GouWuChe.this));
        gouwuche_back_img.setOnClickListener(this);
    }

    /**
     * 找到我们关心的控件,初始化
     */

    private void init() {
        gouwuche_back_img = (ImageView) findViewById(R.id.gouwuche_back_img);//返回按钮
        gouwucge_pay_button = (Button) findViewById(R.id.gouwucge_pay_button);
        gouwuche_listview = (ListView) findViewById(R.id.gouwuche_listview);

    }

    /***
     * 按钮的点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gouwuche_back_img:
                finish();
                break;
            case R.id.gouwucge_pay_button:
                break;




        }

    }
}


