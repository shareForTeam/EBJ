package share.com.ebj;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import share.com.ebj.adapter.Index_Viewpager_adapter;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ResideMenu resideMenu;
    private ImageView im_menu;
    private ResideMenuItem mine, store;
    private Handler handler;
    private ViewPager viewPager;
    private ImageView[] imageView;
    private RadioGroup radioGroup;
    private int icon[] = {R.mipmap.index_lunbo, R.mipmap.index_lunbo, R.mipmap.index_lunbo, R.mipmap.index_lunbo};

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
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MySelfCenterActivity.class);
                startActivity(intent);
                resideMenu.closeMenu();
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "进入商城", Toast.LENGTH_LONG).show();
            }
        });
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
