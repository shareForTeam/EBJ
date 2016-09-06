package share.com.ebj.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/9/1.
 */
public class Index_Viewpager_adapter extends PagerAdapter {
    private ImageView[] imageViews;

    public Index_Viewpager_adapter(ImageView[] imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews.length;
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
        container.addView(imageViews[position], 0);
        return imageViews[position];
    }
}
