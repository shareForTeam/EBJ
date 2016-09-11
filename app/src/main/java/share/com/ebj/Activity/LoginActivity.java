package share.com.ebj.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import share.com.ebj.JavaBean.UserLogin;
import share.com.ebj.R;
import share.com.ebj.SingleUser.UserSingleton;
import share.com.ebj.Utils.DBOperation;
import share.com.ebj.jsonStr.UserJson;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "crazyK";
    //autoCompleteTextView相关
    private AutoCompleteTextView autoCompleteTextView;
    private List<UserLogin> userLoginList;
    //登录
    private ImageView iv_login;
    private EditText et_pwd;

    private final int RESULT_CODE = 0;

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
//                Log.i(TAG, "act开始变化");
                if(userLoginList.size() != 0){
//                    Log.i(TAG, "返回的userLoginList长度不为0 ：" +userLoginList.size());
                    ArrayList<String> nameList = new ArrayList<>();
                    for(int i = 0 ; i < userLoginList.size() ; i++){
                        String name = userLoginList.get(i).getName();
                        nameList.add(name);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(LoginActivity.this,android.R.layout.simple_list_item_1,nameList);
                    autoCompleteTextView.setAdapter(arrayAdapter);
//                    Log.i(TAG, "设置适配器成功 ");
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
                    /**查询本地数据库*/
                    DBOperation dbOperation = new DBOperation();
                    List<UserLogin> userLoginList1 = dbOperation.selectIDPwd();
                    if(userLoginList1.size() != 0){
                        /**在本地数据库查询的个数不为0*/
                        List<String> nameList = new ArrayList<>();
                        List<String> pwdList = new ArrayList<>();
                        List<Integer> user_idList = new ArrayList<>();
                        for(int i = 0 ; i <userLoginList1.size() ; i++){
                            String name1 = userLoginList1.get(i).getName();
                            String pwd1 = userLoginList1.get(i).getPwd();
                            int user_id = userLoginList1.get(i).getUser_id();
                            nameList.add(name1);
                            pwdList.add(pwd1);
                            user_idList.add(user_id);
                        }
                        if(nameList.contains(name)){
                            /**如果数据存在于本地数据库*/
                            int indexOf = nameList.indexOf(name);
                            String conter_pwd = pwdList.get(indexOf);
                            int conter_user_id = user_idList.get(indexOf);
                            if(TextUtils.equals(pwd,conter_pwd)){
                                /**且账号密码匹配*/
//                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                SharedPreferences loginSP = getSharedPreferences("user_id", MODE_PRIVATE);
                                SharedPreferences.Editor editor = loginSP.edit();
                                editor.putInt("user_id",conter_user_id);
                                editor.apply();
                                this.finish();
                            }
                        }else {
                            /**本地数据库不存在，查询服务器
                             * 并且更新本地数据库
                             * 并且更新SharePreference
                             * */
                            getUserOnServ(name,pwd);

                        }

                    }else {
                        /**如果在本地数据库中没有数据*/
                        /**本地数据库不存在，查询服务器
                         * 并且更新本地数据库
                         * 并且更新SharePreference
                         * */
                        getUserOnServ(name,pwd);
                    }

                }

                break;
        }
    }

    public void getUserOnServ(final String name, final String pwd){
        /**本地数据库不存在，查询服务器*/
        String url = "http://172.18.4.18:8080/EBJ_Project/user_info.do";
        RequestParams params = new RequestParams(url);
        params.addParameter("type","android");
        params.addParameter("query","user");
        params.addParameter("name",name);
        params.addParameter("pwd",pwd);
        x.http().request(HttpMethod.POST, params , new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                /**逻辑为服务器返回json数据，获得json数据后再判断 exist unexit name_error  pwd_error */
                if(result.equals("false")){
                    Toast.makeText(LoginActivity.this, "服务器发现同名用户，请联系官方", Toast.LENGTH_SHORT).show();
                }else {
                    Gson gson = new Gson();
                    UserJson userJson = gson.fromJson(result, UserJson.class);
                    if(userJson.getReason().equals("unexit")){
                        Toast.makeText(LoginActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                    }else if(userJson.getReason().equals("exit")){
                        UserJson.ListBean user = userJson.getList().get(0);
                        String name_serv = user.getName();
                        String pwd_serv = user.getPwd();
                        if(!TextUtils.equals(name,name_serv) || !TextUtils.equals(pwd,pwd_serv) ){/**输入账号密码与服务器不匹配*/
                            Toast.makeText(LoginActivity.this, "账号密码不正确", Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.equals(name,name_serv) && TextUtils.equals(pwd,pwd_serv)){/**登录成功*/
                            /**更新UserSingleton*/
                            String goods_id_serv = user.getGoods_id();
                            String icon_serv = user.getIcon();
                            String self_sign_serv = user.getSelf_sign();
                            int user_id_serv = user.getUser_id();
                            UserSingleton userSingleton = UserSingleton.getInstance();
                            userSingleton.updateUser(user_id_serv,name_serv,pwd_serv,self_sign_serv,icon_serv,goods_id_serv);
                            /**将信息写入本地数据库*/
                            DBOperation dbOperation1 = new DBOperation();
                            dbOperation1.updateDB(user_id_serv,name_serv,pwd_serv,self_sign_serv,icon_serv,goods_id_serv);
                            /**更改SharePreference*/
                            SharedPreferences loginSP = getSharedPreferences("user_id", MODE_PRIVATE);
                            SharedPreferences.Editor editor = loginSP.edit();
                            editor.putInt("user_id",user_id_serv);
                            editor.apply();

                            LoginActivity.this.finish();
                        }
                    }
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
}
