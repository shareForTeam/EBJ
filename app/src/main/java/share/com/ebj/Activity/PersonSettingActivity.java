package share.com.ebj.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import share.com.ebj.R;

/**
 * Created by Administrator on 2016/9/5.
 *
 * 个人中心主函数
 */

public class PersonSettingActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView person_setting_back;
    private LinearLayout nicheng_button,sex_button,gexingqianming_button,changebg_button,erweima_button,change_passward_button;
    private CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_setting);
        init();
        click();

    }

    /***
     * 控件的初始化
     */
    private void init(){
        person_setting_back= (ImageView) findViewById(R.id.person_setting_back);//返回键
        nicheng_button= (LinearLayout) findViewById(R.id.nicheng_button);//昵称设置
        sex_button= (LinearLayout) findViewById(R.id.sex_button);//性别设置
        gexingqianming_button= (LinearLayout) findViewById(R.id.gexingqianming_button);//个性签名设置
        changebg_button= (LinearLayout) findViewById(R.id.changebg_button);//背景更改设置
        erweima_button= (LinearLayout) findViewById(R.id.erweima_button);//二维码设置
        change_passward_button= (LinearLayout) findViewById(R.id.change_passward_button);//更改密码设置
        circleImageView= (CircleImageView) findViewById(R.id.circleImageView);
    }

    /***
     * 点击事件的设置
     */
    private void click(){
        person_setting_back.setOnClickListener(this);
        nicheng_button.setOnClickListener(this);
        sex_button.setOnClickListener(this);
        gexingqianming_button.setOnClickListener(this);
        changebg_button.setOnClickListener(this);
        erweima_button.setOnClickListener(this);
        change_passward_button.setOnClickListener(this);
        circleImageView.setOnClickListener(this);
    }


    /***
     *
     *  点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //返回按钮
            case R.id.person_setting_back:
                finish();
                break;
            //昵称的设置
            case R.id.nicheng_button:
                Toast.makeText(getApplicationContext(),"昵称设置",Toast.LENGTH_SHORT).show();
                break;
            //性别设置
            case R.id.sex_button:
                Toast.makeText(getApplicationContext(),"性别设置",Toast.LENGTH_SHORT).show();
                break;
            //个性签名的设置
            case R.id.gexingqianming_button:
                Toast.makeText(getApplicationContext(),"个性签名的设置",Toast.LENGTH_SHORT).show();
                break;
            //背景更改的设置
            case R.id.changebg_button:
                Toast.makeText(getApplicationContext(),"背景更改的设置",Toast.LENGTH_SHORT).show();
                break;
            //二维码的设置
            case R.id.erweima_button:
                Toast.makeText(getApplicationContext(),"二维码的设置",Toast.LENGTH_SHORT).show();
                break;
            //密码更改的设置
            case R.id.change_passward_button:
                Toast.makeText(getApplicationContext(),"密码更改的设置",Toast.LENGTH_SHORT).show();
                break;
            //头像的设置
            case R.id.circleImageView:
                Toast.makeText(getApplicationContext(),"头像的设置",Toast.LENGTH_SHORT).show();
                break;

        }

    }
}