package share.com.ebj.leadFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import share.com.ebj.Activity.LoginActivity;
import share.com.ebj.R;
import share.com.ebj.SingleUser.UserSingleton;
import share.com.ebj.Utils.StrManager;
import share.com.ebj.adapter.GouWuCheListViewAdapter;

/**
 * Created by Administrator on 2016/9/8.
 */
public class CarFragment extends Fragment implements View.OnClickListener {
    private ImageView gouwuche_back_img;
    private Button gouwucge_pay_button;
    private ListView gouwuche_listview;
    private CheckBox check_all_btn;
    private GouWuCheListViewAdapter gouWuCheListViewAdapter;
    private int user_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_fragment, container, false);
        initView(view);
        initListener();


        gouWuCheListViewAdapter = new GouWuCheListViewAdapter(getActivity(),check_all_btn);
        gouwuche_listview.setAdapter(gouWuCheListViewAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        isLogin();
    }

    public void initView(View view){
        gouwuche_back_img = (ImageView) view.findViewById(R.id.gouwuche_back_img);//返回按钮
        gouwucge_pay_button = (Button) view.findViewById(R.id.gouwucge_pay_button);
        gouwuche_listview = (ListView) view.findViewById(R.id.gouwuche_listview);
        check_all_btn = (CheckBox) view.findViewById(R.id.check_button);
    }

    public void initListener(){
        gouwuche_back_img.setOnClickListener(this);
        check_all_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                gouWuCheListViewAdapter.notifyDataSetChanged();
            }
        });
    }

    /**判断是否登录，若登录设置adapter、listview*/
    public void isLogin(){
        SharedPreferences login_SP = getActivity().getSharedPreferences("user_id", Context.MODE_PRIVATE);
        user_id = login_SP.getInt("user_id", -1);
        if(user_id != -1){
            /**已登录*/
            UserSingleton userSingleton = UserSingleton.getInstance();
            String goods_id = userSingleton.getGoods_id();
            StrManager strManager = new StrManager();
            List<String> goods_list = strManager.getIconList(goods_id);
            gouWuCheListViewAdapter.setGoodsList(goods_list);
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
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gouwuche_back_img:
                getActivity().finish();
                break;
            case R.id.gouwucge_pay_button:
                break;

        }
    }
}