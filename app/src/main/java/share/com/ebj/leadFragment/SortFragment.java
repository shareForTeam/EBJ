package share.com.ebj.leadFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import share.com.ebj.R;
import share.com.ebj.adapter.SortViewPagerAdapter;

/**
 * Created by Administrator on 2016/9/8.
 */
public class SortFragment extends Fragment {
    private String TAG = "crazyK";
    private TabLayout tabLayout;
    private ViewPager viewPager;//四大分类的viewpager
    private FragmentManager fragmentManager;
    private ImageView pay_click_back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_fragment, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.activity_sort_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.activity_sort_tablayout);
        pay_click_back = (ImageView) view.findViewById(R.id.pay_click_back);
        tabLayout.setupWithViewPager(viewPager);//把TabLayout和ViewPager关联起来
        fragmentManager = getActivity().getSupportFragmentManager();//获取Fragment管理者
        SortViewPagerAdapter adapter = new SortViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);//设置适配器
        viewPager.setOffscreenPageLimit(4);
        whichSelect();
        pay_click_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });


        return view;
    }

    public void whichSelect() {
        Intent intent = getActivity().getIntent();
        int index_sortFragment = intent.getIntExtra("index_sortFragment", -1);
        if (index_sortFragment == -1) {
            return;
        } else {
            viewPager.setCurrentItem(index_sortFragment, true);

        }
    }


}
