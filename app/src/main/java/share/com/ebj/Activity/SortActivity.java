package share.com.ebj.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import share.com.ebj.jsonStr.SortJson;
import share.com.ebj.R;
import share.com.ebj.Utils.IconStr_To_List;
import share.com.ebj.adapter.SortViewPagerAdapter;
import share.com.ebj.leadFragment.Sort_Fragment_ACC;
import share.com.ebj.leadFragment.Sort_Fragment_clothes;
import share.com.ebj.leadFragment.Sort_Fragment_shoes;
import share.com.ebj.leadFragment.Sort_Fragment_skirt;

public class SortActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragmentsList = new ArrayList<>();
    private FragmentManager fragmentManager;
    private String TAG = "crazyK";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Sort_Fragment_clothes fragment_clothes;//上衣
    private Sort_Fragment_shoes fragment_shose;//鞋子
    private Sort_Fragment_skirt fragment_skirt;//下衣
    private Sort_Fragment_ACC fragment_ACC;//配饰



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        init();
        tabLayout.setupWithViewPager(viewPager);//把TabLayout和ViewPager关联起来
        fragmentManager = getSupportFragmentManager();//获取Fragment管理者
        SortViewPagerAdapter adapter = new SortViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);//设置适配器
        /**接收mainActivity传过来的product_id*/
        Intent intent = getIntent();
        int product_id = intent.getIntExtra("product_id", -1);
        getSortInfo(product_id);



    }
    private  void  init(){
        viewPager = (ViewPager) findViewById(R.id.activity_sort_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.activity_sort_tablayout);
    }



    /**
     * 开始访问服务器，获取分类Json
     * 并将获得的json转化成bean
     * */
    public void getSortInfo(int product_id){
        if(product_id == -1){
            return;
        }
     */
    public void getSortInfo(int product_id) {
        RequestParams requestParams = new RequestParams("http://172.18.4.18:8080/EBJ_Project/goods_sort.do");
        requestParams.addParameter("type", "android");
        requestParams.addParameter("query", "sort");
        requestParams.addParameter("product_id", product_id);
        x.http().request(HttpMethod.POST, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.i(TAG, "onSuccess: "+result);
                Gson gson = new Gson();
                SortJson sortJson = gson.fromJson(result, SortJson.class);

                Log.i(TAG, "onSuccess: " + result);
            }

            /**isOnCallback :true:本地onSuceess中逻辑错误
             * false：远程服务器错误
             * */
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "onCancelled: ");
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "onFinished: ");
            }
        });
    }
}
