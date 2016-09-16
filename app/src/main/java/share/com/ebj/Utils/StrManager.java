package share.com.ebj.Utils;

/**
 * Created by Administrator on 2016/9/7.
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于将json返回的icon字符串，以分号split，组合成所需集合
 * */
public class StrManager {
    private String TAG = "crazyK";

    public List<String> getIconList(String jsonStr) {
        String[] splitIconStr = jsonStr.split(";");
        ArrayList<String> iconList = new ArrayList<>();

        for (String string : splitIconStr) {
            if (string.equals("null")|| string.equals("")) {
                continue;
            } else {
                iconList.add(string);
            }
        }

//        for (int i = 0; i < splitIconStr.length; i++) {
//            iconList.add(splitIconStr[i]);
////            Log.i(TAG, "getIconList: " + splitIconStr[i] + "\t");
//        }
        return iconList;

    }

    public  String deleteGoods_id(String user_goods_id, String delete_goods_id) {
        /**
         * 注意：str.subString(start,end),为前闭后开区间
         * */
        if (user_goods_id.contains(delete_goods_id)) {
            int position = user_goods_id.indexOf(delete_goods_id);
            int src_Length = user_goods_id.length();
            int del_Length = delete_goods_id.length();
            String new_goods_id = user_goods_id.substring(0, position - 1) + user_goods_id.substring(position + del_Length, src_Length);

            return new_goods_id;
        } else {
            Log.i(TAG, "UserSingleton --> deleteGoods_id 失败，delete_goods_id不存在 ");
            return null;
        }
    }

    public String addGoods_id(String user_goods_id ,String add_goods_id) {
        String new_goods_id = user_goods_id + ";" + add_goods_id;
        return new_goods_id;
    }
}