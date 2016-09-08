package share.com.ebj.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import share.com.ebj.leadFragment.Sort_Fragment_ACC;
import share.com.ebj.leadFragment.Sort_Fragment_clothes;
import share.com.ebj.leadFragment.Sort_Fragment_shoes;
import share.com.ebj.leadFragment.Sort_Fragment_skirt;

/**
 * Created by Administrator on 2016/9/7.
 */

/***
 * ViewPager里面嵌套Fragment
 */
public class SortViewPagerAdapter extends FragmentPagerAdapter {

    String header_Title[] = {"上衣", "下衣", "鞋子", "配饰"};//四个分类的标题
    Fragment fragment [];//具体的分类信息，四个Fragment

//构造四个Fragment
    public SortViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragment = new Fragment[4];
        fragment[0] = new Sort_Fragment_clothes();
        fragment[1] = new Sort_Fragment_skirt();
        fragment[2] = new Sort_Fragment_shoes();
        fragment[3] = new Sort_Fragment_ACC();

    }

    @Override
    public Fragment getItem(int position) {
        return fragment[position];
    }

    @Override
    public int getCount() {
        return fragment.length;
    }


    //这里设置的是tablayout的title标题内容
    @Override
    public CharSequence getPageTitle(int position) {
        return header_Title[position];
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
