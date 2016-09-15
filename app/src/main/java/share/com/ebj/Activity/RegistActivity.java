package share.com.ebj.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import share.com.ebj.R;
import share.com.ebj.SingleUser.UserSingleton;
import share.com.ebj.Utils.StrManager;
import share.com.ebj.init.InitActivity;
import share.com.ebj.sqlite.User_Info;

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
                String name_input = et_username.getText().toString().trim();
                String pwd_input = et_pwd.getText().toString().trim();

                RequestParams params = new RequestParams("http://172.18.4.18:8080/EBJ_Project/regist.do");
                params.addParameter("type","android");
                params.addParameter("query","regist");
                params.addParameter("name",name_input);
                params.addParameter("pwd",pwd_input);
                x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        /**判断返回信息：“服务器数据出错”，“用户名不唯一”，“用户名已存在”，正确的数据：user_id;name;pwd*/
                        if(result.equals("服务器数据出错")){
                            Toast.makeText(RegistActivity.this, "服务器数据出错，请联系官方客服", Toast.LENGTH_SHORT).show();
                        }else if(result.equals("用户名不唯一")){
                            Toast.makeText(RegistActivity.this, "服务器数据出错，请联系官方客服", Toast.LENGTH_SHORT).show();
                        }else if(result.equals("用户名已存在")){
                            Toast.makeText(RegistActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                        }else {
                            StrManager strManager = new StrManager();
                            List<String> list = strManager.getIconList(result);
                            /**更新UserSingleton*/
                            UserSingleton userSingleton = UserSingleton.getInstance();
                            userSingleton.setUser_id(Integer.parseInt(list.get(0)));
                            userSingleton.setName(list.get(1));
                            userSingleton.setPwd(list.get(2));
                            /**更新SharePreference*/
                            SharedPreferences loginSP = getSharedPreferences("user_id", MODE_PRIVATE);
                            SharedPreferences.Editor editor = loginSP.edit();
                            editor.putInt("user_id",Integer.parseInt(list.get(0)));
                            editor.apply();
                            /**更新本地数据库*/
                            DbManager dbManager = x.getDb(InitActivity.daoConfig);
                            try {
                                dbManager.save(new User_Info(Integer.parseInt(list.get(0)),list.get(1),list.get(2),null,null,null));
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            RegistActivity.this.finish();
                        }
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
