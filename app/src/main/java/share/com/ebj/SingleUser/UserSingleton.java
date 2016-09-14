package share.com.ebj.SingleUser;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/9/11.
 */
public class UserSingleton {
    private String TAG = "crazyK";
    private int user_id;
    private String name;
    private String pwd;
    private String self_sign;
    private String icon;
    private String goods_id;

    private static class LazyHolder {
        private static final UserSingleton INSTANCE = new UserSingleton();
    }

    private UserSingleton() {
    }

    public static final UserSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void updateUser(int user_id ,String name ,String pwd ,String self_sign ,String icon ,String goods_id ){
        this.user_id = user_id;
        this.name =name;
        this.pwd =pwd;
        this.self_sign = self_sign;
        this.icon = icon;
        this.goods_id = goods_id;
    }


//    public String addGoods_id(String add_goods_id) {
//        String new_goods_id = this.goods_id + ";" + add_goods_id;
//        this.goods_id = new_goods_id;
//        return new_goods_id;
//    }

//    public List<String> getGoods_id_List() {
//        String[] split = this.goods_id.split(";");
//        ArrayList<String> arrayList = new ArrayList<>();
//        for (String string : split) {
//            if (string.equals("null")|| string.equals("")) {
//                continue;
//            } else {
//                arrayList.add(string);
//            }
//        }
//        return arrayList;
//    }


//    public String deleteGoods_id(String delete_goods_id) {
//        /**
//         * 注意：str.subString(start,end),为前闭后开区间
//         * */
//        if (this.goods_id.contains(delete_goods_id)) {
//            int position = this.goods_id.indexOf(delete_goods_id);
//            int src_Length = this.goods_id.length();
//            int del_Length = delete_goods_id.length();
//            String new_goods_id = this.goods_id.substring(0, position - 1) + this.goods_id.substring(position+del_Length ,src_Length);
//            this.goods_id = new_goods_id;
//            return new_goods_id;
//        } else {
//            Log.i(TAG, "UserSingleton --> deleteGoods_id 失败，delete_goods_id不存在 ");
//            return null;
//        }
//
//    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSelf_sign() {
        return self_sign;
    }

    public void setSelf_sign(String self_sign) {
        this.self_sign = self_sign;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }


}
