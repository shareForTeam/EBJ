package share.com.ebj.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import share.com.ebj.JavaBean.UserLogin;
import share.com.ebj.R;
import share.com.ebj.Utils.DBOperation;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //autoCompleteTextView相关
    private AutoCompleteTextView autoCompleteTextView;
    private List<UserLogin> userLoginList;
    //登录
    private ImageView iv_login;
    private EditText et_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initListener();

    }

    public void initView(){
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.login_act_user_id);
        et_pwd = (EditText) findViewById(R.id.login_pwd);
        iv_login = (ImageView) findViewById(R.id.login_login);
    }

    public void initListener(){
        iv_login.setOnClickListener(this);

        /**addTextChangedListener*/
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DBOperation dbOperation = new DBOperation();
                userLoginList = dbOperation.selectIDPwd();//返回来的用户登录信息集合
                if(userLoginList.size() != 0){
                    ArrayList<String> nameList = new ArrayList<String>();
                    for(int i = 0 ; i < userLoginList.size() ; i++){
                        String name = userLoginList.get(i).getName();
                        nameList.add(name);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(LoginActivity.this,android.R.layout.simple_list_item_1,nameList);
                    autoCompleteTextView.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**setOnItemClickListener*/
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pwd = userLoginList.get(i).getPwd();
                et_pwd.setText(pwd);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_login:
                String name = autoCompleteTextView.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();

                if(TextUtils.isEmpty(name)|| TextUtils.isEmpty(pwd)){
                    Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                    //不为空时，先查询数据库，若无数据则查询服务器
                }else if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)){
                    DBOperation dbOperation = new DBOperation();
                    List<UserLogin> userLoginList1 = dbOperation.selectIDPwd();
                    if(userLoginList1.size() != 0){
                        List<String> nameList = new ArrayList<>();
                        List<String> pwdList = new ArrayList<>();
                        for(int i = 0 ; i <userLoginList1.size() ; i++){
                            String name1 = userLoginList1.get(i).getName();
                            String pwd1 = userLoginList1.get(i).getPwd();
                            nameList.add(name1);
                            pwdList.add(pwd1);
                        }
                        if(nameList.contains(name)){
                            int indexOf = nameList.indexOf(name);
                            String conter_pwd = pwdList.get(indexOf);
                            if(conter_pwd.equals(pwd)){
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                this.finish();
                                // TODO: 2016/9/10 登录成功后的逻辑
                            }
                        }else {
                            // TODO: 2016/9/10 本地数据库不存在，查询服务器
                            String url = "http://172.18.4.18:8080/EBJ_Project/user_info.do";
                            RequestParams params = new RequestParams(url);
                            params.addParameter("type","android");
                            params.addParameter("query","user");
                            params.addParameter("name",name);
                            params.addParameter("pwd",pwd);
                            x.http().request(HttpMethod.POST, params , new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    if(result.equals("exist")){
                                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        // TODO: 2016/9/10 服务器找到数据，1、将账号密码写入本地数据库，2、执行登录成功后的逻辑
                                        finish();
                                    }else if(result.equals("name_error")){
                                        Toast.makeText(LoginActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                                    }else if(result.equals("pwd_error")){
                                        Toast.makeText(LoginActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
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
                        }

                    }else {
                        Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                    }

                }

                break;
        }
    }
}
