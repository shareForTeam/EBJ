package share.com.ebj.leadFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import share.com.ebj.Activity.ProductActivity;
import share.com.ebj.R;
import share.com.ebj.Utils.IconStr_To_List;
import share.com.ebj.jsonStr.SortJson;
import share.com.ebj.recycleview.RecycleView_Adapter;

/**
 * Created by Administrator on 2016/9/7.
 */

public class Sort_Fragment_ACC extends Fragment implements RecycleView_Adapter.OnRVItemClickListener{
    private RecyclerView rv_acc;
    private RecycleView_Adapter adapter;
    private Activity activity;
    private List<String> iconStr_List;
    private List<String> nameStr_List;
    private List<String> prizeStr_List;
    private SortJson sortJson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_fragment_acc,container,false);
        activity = getActivity();
        rv_acc = (RecyclerView) view.findViewById(R.id.id_fragment_acc_rv);
        rv_acc.setLayoutManager(new GridLayoutManager(getActivity(),2));

        iconStr_List = new ArrayList<>();
        nameStr_List = new ArrayList<>();
        prizeStr_List = new ArrayList<>();

        adapter = new RecycleView_Adapter(activity);
        adapter.setOnRVItemClickListener(this);
        rv_acc.setAdapter(adapter);


        getSortInfo(4);

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
        if(product_id == 4){
            RequestParams requestParams = new RequestParams("http://172.18.4.18:8080/EBJ_Project/goods_sort.do");
            requestParams.addParameter("type", "android");
            requestParams.addParameter("query", "sort");
            requestParams.addParameter("product_id", product_id);
            x.http().request(HttpMethod.POST, requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
//                Log.i(TAG, "onSuccess: "+result);
                    Gson gson = new Gson();
                    sortJson = gson.fromJson(result, SortJson.class);
                    for(int i = 0; i < sortJson.getList().size() ; i++){
                        String goods_icon_Str = sortJson.getList().get(i).getGoods_icon();
                        String goods_name = sortJson.getList().get(i).getGoods_name();
                        String goods_price = sortJson.getList().get(i).getGoods_price();

                        nameStr_List.add(goods_name);
                        prizeStr_List.add(goods_price);

                        IconStr_To_List iconStr_to_list = new IconStr_To_List();
                        List<String> iconList = iconStr_to_list.getIconList(goods_icon_Str);
                        String first_icon = iconList.get(0);
                        iconStr_List.add(first_icon);
                    }
                    adapter.setIconStr_List(iconStr_List,nameStr_List,prizeStr_List);
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

    @Override
    public void onRVItemClick(View view, int position) {
        int goods_id = sortJson.getList().get(position).getGoods_id();
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra("goods_id",goods_id);
        startActivity(intent);
    }
}
