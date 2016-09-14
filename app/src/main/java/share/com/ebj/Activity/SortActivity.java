package share.com.ebj.Activity;

import android.content.Intent;
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
import share.com.ebj.adapter.MyViewPager;
import share.com.ebj.adapter.SortPager_Adapter;
import share.com.ebj.leadFragment.CarFragment;
import share.com.ebj.leadFragment.MySelfFragment;
import share.com.ebj.leadFragment.SortFragment;

public class SortActivity extends AppCompatActivity {
    private String TAG = "crazyK";
    private List<Fragment> fragments;
    private MyViewPager sort_viewPager;
    private RadioGroup sort_group;


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
        int user_id = intentFromMain.getIntExtra("user_id", -1);
        if(user_id != -1){
            sort_viewPager.setCurrentItem(2);
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
                        break;
                    case R.id.sort_rb3:
                        // TODO: 2016/9/13 判断是否登录
                        sort_viewPager.setCurrentItem(1);
                        break;
                    case R.id.sort_rb4:
                        sort_viewPager.setCurrentItem(2);
                        break;
                }
            }
        });

    }

    private void init() {
        sort_viewPager = (MyViewPager) findViewById(R.id.main_Layout);
        sort_group = (RadioGroup) findViewById(R.id.sort_radio_group);
    }


}
