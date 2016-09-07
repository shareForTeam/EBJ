package share.com.ebj.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.MalformedURLException;
import java.net.URL;

import share.com.ebj.R;

public class SortActivity extends AppCompatActivity {
    private String TAG = "crazyK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        /**接收mainActivity传过来的product_id*/

    }

    /**开始访问服务器，获取分类Json
     * 并将获得的json转化成bean
     * */
    public void getSortInfo(int product_id){
        RequestParams requestParams = new RequestParams("http://172.18.4.18:8080/EBJ_Project/goods_sort.do");
        requestParams.addParameter("type","android");
        requestParams.addParameter("query","sort");
        requestParams.addParameter("product_id",product_id);
        x.http().request(HttpMethod.POST,requestParams , new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: "+result);
            }

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
