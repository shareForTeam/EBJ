package share.com.ebj.leadFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import share.com.ebj.Activity.LoginActivity;
import share.com.ebj.Activity.PersonSettingActivity;
import share.com.ebj.R;
import share.com.ebj.SingleUser.UserSingleton;

/**
 * Created by Administrator on 2016/9/8.
 */
public class MySelfFragment extends Fragment implements View.OnClickListener {
    private String TAG = "crazyK";
    private RelativeLayout rv_accountSetting;
    private CircleImageView civ_touxiang;
    private TextView tv_name,tv_self_sign;
    private final int REQUEST_CODE = 0;
    private int user_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_setting,container,false);
        initView(view);
        initListener();
//        isLogin();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        isLogin();
    }

    public void initView(View view){
        rv_accountSetting = (RelativeLayout) view.findViewById(R.id.fragment_setting_account_setting);
        civ_touxiang = (CircleImageView) view.findViewById(R.id.fragment_setting_touxiang);
        tv_name= (TextView) view.findViewById(R.id.fragment_setting_name);
        tv_self_sign = (TextView) view.findViewById(R.id.fragment_setting_self_sign);
    }

    public void initListener(){
        rv_accountSetting.setOnClickListener(this);
        tv_name.setOnClickListener(this);
    }

    /**判断是否登录，如果登录获取个人信息更新相关控件*/
    public void isLogin(){
        SharedPreferences login_SP = getActivity().getSharedPreferences("user_id", Context.MODE_PRIVATE);
        user_id = login_SP.getInt("user_id", -1);
        if(user_id !=-1){
            /**已登录状态*/
            UserSingleton userSingleton = UserSingleton.getInstance();
            String icon = userSingleton.getIcon();
            String name = userSingleton.getName();
            String self_sign = userSingleton.getSelf_sign();

            tv_name.setText(name);

            if(icon != null){
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(icon,civ_touxiang);
                if(civ_touxiang.getVisibility() == View.INVISIBLE){
                    civ_touxiang.setVisibility(View.VISIBLE);
                }
            }
            if(self_sign != null){
                tv_self_sign.setText(self_sign);
            }
        }else {
            /**为登录状态*/
            if(civ_touxiang.getVisibility() == View.VISIBLE){
                civ_touxiang.setVisibility(View.INVISIBLE);
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /**账户设置*/
            case R.id.fragment_setting_account_setting:
                SharedPreferences login_SP = getActivity().getSharedPreferences("user_id", Context.MODE_PRIVATE);
                int user_id = login_SP.getInt("user_id", -1);
                if(user_id != -1){
                    Intent intent_MyselfToSettingCenter = new Intent(getActivity(), PersonSettingActivity.class);
                    startActivity(intent_MyselfToSettingCenter);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(null)
                            .setMessage("您还未登录，请问是否登录？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent_ProductToLogin = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent_ProductToLogin);
                                }
                            })
                            .setNegativeButton("否", null)
                            .create().show();
                }

                break;

            /**点击登录/注册*/
            case R.id.fragment_setting_name:
                if(this.user_id == -1){
                    Intent intent_MyselfToLogin = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent_MyselfToLogin);
                }
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == 0 &&resultCode == 0){
//            if(data != null){
//                String touxiang_path = data.getStringExtra("touxiang_path");
//                Bitmap touxiang = BitmapFactory.decodeFile(touxiang_path);
//                civ_touxiang.setImageBitmap(touxiang);
//            }
//
//        }
//    }
}
