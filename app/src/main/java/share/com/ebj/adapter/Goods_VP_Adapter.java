package share.com.ebj.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public class Goods_VP_Adapter extends PagerAdapter {
    private List<String> iconStr_List = new ArrayList<>();


    public void setIconStr_List(List<String> iconStr_List){
        this.iconStr_List = iconStr_List;
    }

    @Override
    public int getCount() {
        return iconStr_List.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public Object instantiateItem(ViewGroup container, int position) {

        ImageView childAt = new ImageView(container.getContext());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(iconStr_List.get(position),childAt);
        container.addView(childAt,0);
        return childAt;
//        container.addView(imageViews[position], 0);
//        return imageViews[position];
    }

//    public List<>
}
