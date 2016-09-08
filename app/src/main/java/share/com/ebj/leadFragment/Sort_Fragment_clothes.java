package share.com.ebj.leadFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import share.com.ebj.R;
import share.com.ebj.Utils.IconStr_To_List;
import share.com.ebj.jsonStr.SortJson;
import share.com.ebj.recycleview.RecycleView_Adapter;

/**
 * Created by Administrator on 2016/9/7.
 */

public class Sort_Fragment_clothes extends Fragment{
    private RecyclerView rv_clothes;
    private RecycleView_Adapter adapter;
    private Activity activity;
    private List<String> picUrls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_fragment_clothes,container,false);
        activity = getActivity();
        rv_clothes = (RecyclerView) view.findViewById(R.id.id_fragment_clothes_rv);
        rv_clothes.setLayoutManager(new GridLayoutManager(getActivity(),2));

        picUrls = new ArrayList<>();
        adapter = new RecycleView_Adapter(activity);

        rv_clothes.setAdapter(adapter);


        getSortInfo(activity.getIntent().getIntExtra("product_id",-1));



        return view;
    }

    /**
     * 开始访问服务器，获取分类Json
     * 并将获得的json转化成bean
     * */
    public void getSortInfo(int product_id){
        if(product_id == -1){
            return;
        }
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
                for(int i = 0 ; i < sortJson.getList().size() ; i++){
                    String goods_icon_Str = sortJson.getList().get(i).getGoods_icon();
                    IconStr_To_List iconStr_to_list = new IconStr_To_List();
                    List<String> iconList = iconStr_to_list.getIconList(goods_icon_Str);
                    String first_icon = iconList.get(0);
                    picUrls.add(first_icon);
                }
                adapter.setIconStr_List(picUrls);
            }

            /**isOnCallback :true:本地onSuceess中逻辑错误
             * false：远程服务器错误
             * */
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }


}