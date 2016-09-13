package share.com.ebj.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import share.com.ebj.R;
import share.com.ebj.adapter.Index_Viewpager_adapter;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ResideMenu resideMenu;
    private ImageView im_menu;
    private ResideMenuItem mine, store;
    private Handler handler;
    private ViewPager viewPager;
    private ImageView[] imageView;
    private RadioGroup radioGroup;
    private int icon[] = {R.mipmap.index_lunbo, R.mipmap.index_lunbo, R.mipmap.index_lunbo, R.mipmap.index_lunbo};
    /**四大分类*/
    private ImageView iv_sort_1,iv_sort_2,iv_sort_3,iv_sort_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.index_navigation);
        im_menu = (ImageView) findViewById(R.id.index_menu);
        viewPager = (ViewPager) findViewById(R.id.index_viewpager);
        viewPager.addOnPageChangeListener(this);
        viewPagerMethod();

        resideMenu = new ResideMenu(this);
        resideMenu.setBackgroundResource(R.mipmap.index_background);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.6f);
        mine = new ResideMenuItem(this, R.mipmap.person, "个人中心");
        store = new ResideMenuItem(this, R.mipmap.store, "进入商城");
        /**添加菜单选项*/
        resideMenu.addMenuItem(mine, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(store, ResideMenu.DIRECTION_LEFT);
        /**禁止手势滑动*/
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
        im_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, MySelfCenterActivity.class);
//                startActivity(intent);
                SharedPreferences login_SP = getSharedPreferences("user_id", MODE_PRIVATE);
                int user_id = login_SP.getInt("user_id", -1);
                if(user_id == -1){
                    Intent intent_MainToLogin = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent_MainToLogin);
                }else {
                    Intent intent_MainToSort = new Intent(MainActivity.this,SortActivity.class);

                    intent_MainToSort.putExtra("user_id",user_id);
                    startActivity(intent_MainToSort);

                    resideMenu.closeMenu();
                }

            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "进入商城", Toast.LENGTH_LONG).show();
            }
        });

        /**黄垲程逻辑*/
        initView();
        initListener();
    }

    /**initView*/
    public void initView(){
        iv_sort_1 = (ImageView) findViewById(R.id.id_main_one_cloes);
        iv_sort_2 = (ImageView) findViewById(R.id.id_main_two_pants);
        iv_sort_3 = (ImageView) findViewById(R.id.id_main_three_shoes);
        iv_sort_4 = (ImageView) findViewById(R.id.id_main_four_acc);
    }

    /**inintListener*/
    public void initListener(){
        iv_sort_1.setOnClickListener(this);
        iv_sort_2.setOnClickListener(this);
        iv_sort_3.setOnClickListener(this);
        iv_sort_4.setOnClickListener(this);
    }

    public void viewPagerMethod() {

        imageView = new ImageView[icon.length];
        for (int i = 0; i < icon.length; i++) {
            ImageView iv = new ImageView(MainActivity.this);
            iv.setBackgroundResource(icon[i]);
            imageView[i] = iv;
        }
        viewPager.setAdapter(new Index_Viewpager_adapter(imageView));
        handler = new Handler() {
            int position = 0;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (viewPager.getCurrentItem() == icon.length - 1) {
                    position = 0;
                } else {
                    position = viewPager.getCurrentItem() + 1;
                }
                viewPager.setCurrentItem(position, true);

            }
        };
        new ViewPagerThread().start();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
        radioButton.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_main_one_cloes:
                Intent intent1 = new Intent(this,SortActivity.class);
                intent1.putExtra("product_id",1);
                intent1.putExtra("index_sortFragment",0);
                startActivity(intent1);
                break;
            case R.id.id_main_two_pants:
                Intent intent2 = new Intent(this,SortActivity.class);
                intent2.putExtra("product_id",2);
                intent2.putExtra("index_sortFragment",1);
                startActivity(intent2);
                break;
            case R.id.id_main_three_shoes:
                Intent intent3 = new Intent(this,SortActivity.class);
                intent3.putExtra("product_id",3);
                intent3.putExtra("index_sortFragment",2);
                startActivity(intent3);
                break;
            case R.id.id_main_four_acc:
                Intent intent4 = new Intent(this,SortActivity.class);
                intent4.putExtra("product_id",4);
                intent4.putExtra("index_sortFragment",3);
                startActivity(intent4);
                break;
        }
    }

    class ViewPagerThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    }



}
