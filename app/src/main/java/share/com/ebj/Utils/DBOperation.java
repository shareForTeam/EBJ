package share.com.ebj.Utils;

import android.util.Log;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import share.com.ebj.JavaBean.UserLogin;
import share.com.ebj.SingleUser.UserSingleton;
import share.com.ebj.init.InitActivity;
import share.com.ebj.sqlite.User_Info;

/**
 * Created by Administrator on 2016/9/10.
 */
public class DBOperation {
    private String TAG = "crazyK";

    /**查询本地数据库完成登录界面的autocompletetext*/
    public List<UserLogin>  selectIDPwd(){
        DbManager dbManager = x.getDb(InitActivity.daoConfig);
        String sql = "select user_id,name,pwd from user";
        List<UserLogin> userLoginList = new ArrayList<>();
        Log.i(TAG, userLoginList.size()+"");
        try {
            /**如果这里没有这个表时，catch抛出异常： no such table: user*/
            List<DbModel> dbModelAll = dbManager.findDbModelAll(new SqlInfo(sql));
            for(DbModel dbModel : dbModelAll){
                UserLogin userLogin = new UserLogin();
                String user_id = dbModel.getString("user_id");
                String name = dbModel.getString("name");
                String pwd = dbModel.getString("pwd");
                userLogin.setUser_id(Integer.parseInt(user_id));
                userLogin.setName(name);
                userLogin.setPwd(pwd);
                userLoginList.add(userLogin);
            }
            return userLoginList;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return userLoginList;
    }

    /**向本地数据库user修改购物车goods_id*/
    public boolean addGoods_id_To_ShopCar(int user_id,String new_goods_id){
        DbManager dbManager = x.getDb(InitActivity.daoConfig);
        try {
            WhereBuilder whereBuilder = WhereBuilder.b();
            whereBuilder.and("user_id","=",user_id);
            KeyValue keyValue = new KeyValue("goods_id",new_goods_id);
            dbManager.update(User_Info.class,whereBuilder,keyValue);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**当点击登录后，访问服务器，将服务器的个人信息写入本地数据库*/
    public boolean updateDB(int user_id ,String name ,String pwd ,String self_sign ,String icon ,String goods_id ){
        DbManager dbManager = x.getDb(InitActivity.daoConfig);
        try {
            dbManager.saveOrUpdate(new User_Info(user_id , name , pwd , self_sign , icon , goods_id));
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }
}
