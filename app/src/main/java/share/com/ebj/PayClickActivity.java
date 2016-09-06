package share.com.ebj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/5.
 * 支付点击主函数
 */

public class PayClickActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView pay_click_back;
    private Button pay_click_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_click);
        init();
        click();

    }
    /***
     * 控件的初始化
     */
    private void init(){
        pay_click_back= (ImageView) findViewById(R.id.pay_click_back);//返回键
        pay_click_button= (Button) findViewById(R.id.pay_click_button);//确认支付按钮

    }

    /***
     * 点击事件的设置
     */
    private void click(){
        pay_click_back.setOnClickListener(this);
        pay_click_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pay_click_back:
                finish();
                break;
            case R.id.pay_click_button:
                Toast.makeText(getApplicationContext(),"确认支付",Toast.LENGTH_SHORT).show();
                break;



        }

    }
}


