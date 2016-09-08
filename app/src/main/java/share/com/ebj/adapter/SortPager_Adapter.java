package share.com.ebj.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8.
 */
public class SortPager_Adapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentlist;

    public SortPager_Adapter(FragmentManager fm, List<Fragment> fragmentlist) {
        super(fm);
        this.fragmentlist = fragmentlist;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        if (fragmentlist == null) {
            return 0;
        }
        return fragmentlist.size();
    }
}
