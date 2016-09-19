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

    public void updateUser_clear(String name ,String pwd ,String self_sign ,String icon ,String goods_id ){
        this.name =name;
        this.pwd =pwd;
        this.self_sign = self_sign;
        this.icon = icon;
        this.goods_id = goods_id;
    }




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
