package share.com.ebj.Utils;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import share.com.ebj.jsonStr.SortJson;

/**
 * Created by Administrator on 2016/9/8.
 */
public class GetSortInfo_pageChange {
    /*public static void getSortInfo_pageChange(int product_id_pageChange , ){
        if(product_id_pageChange == -1){
            return;
        }
        RequestParams requestParams = new RequestParams("http://172.18.4.18:8080/EBJ_Project/goods_sort.do");
        requestParams.addParameter("type", "android");
        requestParams.addParameter("query", "sort");
        requestParams.addParameter("product_id", product_id_pageChange);
        x.http().request(HttpMethod.POST, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.i(TAG, "onSuccess: "+result);
                Gson gson = new Gson();
                SortJson sortJson = gson.fromJson(result, SortJson.class);
                for(int i = 0 ; i < sortJson.getList().size() ; i++){
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
             *
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
//    }*/
}
