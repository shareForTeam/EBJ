package share.com.ebj.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import share.com.ebj.R;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "crazyK";
    private EditText et_username,et_pwd;
    private ImageView iv_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initView();
        initListener();
    }


    public void initView(){
        et_username = (EditText) findViewById(R.id.regist_username);
        et_pwd = (EditText) findViewById(R.id.regist_pwd);
        iv_regist = (ImageView) findViewById(R.id.regist_regist);
    }

    public void initListener(){
        iv_regist.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /**登录*/
            case R.id.regist_regist:
                // TODO: 2016/9/12 1、将账号密码到服务器，2、服务器查询数据库中所有user_name,如果相同返回“用户名已存在”，若不存在 -->写入服务器数据库，将user_id账号密码返回，安卓中写入数据库，更新UserSington
                String name_input = et_username.getText().toString().trim();
                String pwd_input = et_pwd.getText().toString().trim();

                RequestParams params = new RequestParams("");
                params.addParameter("name",name_input);
                params.addParameter("pwd",pwd_input);
                x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<List<String>>() {
                    @Override
                    public void onSuccess(List<String> result) {

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

                break;
        }
    }
}
