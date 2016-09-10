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

import share.com.ebj.R;
import share.com.ebj.adapter.SortViewPagerAdapter;

/**
 * Created by Administrator on 2016/9/8.
 */
public class SortFragment extends Fragment {
    private String TAG = "crazyK";
    private TabLayout tabLayout;
    private ViewPager viewPager ;//四大分类的viewpager
    private FragmentManager fragmentManager;
    private Sort_Fragment_clothes fragment_clothes;//上衣
    private Sort_Fragment_shoes fragment_shose;//鞋子
    private Sort_Fragment_skirt fragment_skirt;//下衣
    private Sort_Fragment_ACC fragment_ACC;//配饰
    public static int product_id_pageChange = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sort_fragment,container,false);
         viewPager = (ViewPager) view.findViewById(R.id.activity_sort_viewpager);
         tabLayout = (TabLayout) view.findViewById(R.id.activity_sort_tablayout);
         tabLayout.setupWithViewPager(viewPager);//把TabLayout和ViewPager关联起来

        fragmentManager = getActivity().getSupportFragmentManager();//获取Fragment管理者
        SortViewPagerAdapter adapter = new SortViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);//设置适配器
        viewPager.setOffscreenPageLimit(4);
        whichSelect();



        return view;
    }

    public void whichSelect(){
        Intent intent = getActivity().getIntent();
        int index_sortFragment = intent.getIntExtra("index_sortFragment",-1);
        if(index_sortFragment == -1){
            return;
        }else {
            viewPager.setCurrentItem(index_sortFragment,true);

        }
    }


}
