package share.com.ebj.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import share.com.ebj.R;
import share.com.ebj.leadFragment.One;
import share.com.ebj.leadFragment.Three;
import share.com.ebj.leadFragment.Two;

public class LeaderActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager viewPager;
    private List fragments;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        radioGroup= (RadioGroup) findViewById(R.id.lead_radiogroup);
        fragments = new ArrayList();
        fragments.add(new One());
        fragments.add(new Two());
        fragments.add(new Three());
        FragmentManager fm = this.getSupportFragmentManager();
        viewPager.setAdapter(new MyPagerAdapater(fm, fragments));
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(this);
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

    class MyPagerAdapater extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public MyPagerAdapater(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            if (fragmentList==null){
                return 0;
            }
            return fragmentList.size();
        }
    }
}
