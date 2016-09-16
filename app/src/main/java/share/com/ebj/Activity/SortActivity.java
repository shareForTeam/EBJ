package share.com.ebj.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import share.com.ebj.R;
import share.com.ebj.SingleUser.UserSingleton;
import share.com.ebj.Utils.StrManager;
import share.com.ebj.adapter.MyViewPager;
import share.com.ebj.adapter.SortPager_Adapter;
import share.com.ebj.leadFragment.CarFragment;
import share.com.ebj.leadFragment.MySelfFragment;
import share.com.ebj.leadFragment.SortFragment;

public class SortActivity extends AppCompatActivity {
    // TODO: 2016/9/14 进行图片二次采样
    private String TAG = "crazyK";
    private List<Fragment> fragments;
    private MyViewPager sort_viewPager;
    private RadioGroup sort_group;
    //上一次选择的是导航栏的哪一个
    private int preChecked = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        init();


        fragments = new ArrayList<Fragment>();
        fragments.add(new SortFragment());
        fragments.add(new CarFragment());
        fragments.add(new MySelfFragment());
        final FragmentManager fm = this.getSupportFragmentManager();
        sort_viewPager.setAdapter(new SortPager_Adapter(fm, fragments));
        sort_viewPager.setOffscreenPageLimit(4);
        /**通过main的个人中心进入*/
        Intent intentFromMain = getIntent();
        int isMine = intentFromMain.getIntExtra("isMine", -1);
        if(isMine == -2){
            sort_viewPager.setCurrentItem(2);
            this.preChecked = 3;
            sort_group.check(R.id.sort_rb4);
        }



        sort_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.sort_rb1:
                        Intent intent = new Intent();
                        intent.setClass(SortActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.sort_rb2:
                        sort_viewPager.setCurrentItem(0);
                        preChecked = 1;
                        break;
                    case R.id.sort_rb3:
                        isLogin();
                        break;
                    case R.id.sort_rb4:
                        sort_viewPager.setCurrentItem(2);
                        preChecked = 3;
                        break;
                }
            }
        });

    }

    private void init() {
        sort_viewPager = (MyViewPager) findViewById(R.id.main_Layout);
        sort_group = (RadioGroup) findViewById(R.id.sort_radio_group);
    }

    public void isLogin(){
        SharedPreferences login_SP = getSharedPreferences("user_id", MODE_PRIVATE);
        int user_id = login_SP.getInt("user_id", -1);
        if(user_id != -1){
            /**已登录*/
            sort_viewPager.setCurrentItem(1);
            this.preChecked = 2;
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(null)
                    .setMessage("您还未登录，请问是否登录？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent_ProductToLogin = new Intent(SortActivity.this, LoginActivity.class);
                            startActivity(intent_ProductToLogin);
                        }
                    })
                    .setNegativeButton("否", null)
                    .create().show();

            /**判断在dialog之前是显示哪一个fragment*/
            if(this.preChecked == 1){
                sort_group.check(R.id.sort_rb2);
            }
            if(this.preChecked == 2){
                sort_group.check(R.id.sort_rb3);
            }
            if(this.preChecked == 3){
                sort_group.check(R.id.sort_rb4);
            }


        }
    }


}
