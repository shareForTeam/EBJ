package share.com.ebj.init;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.xutils.DbManager;
import org.xutils.config.DbConfigs;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.table.DbModel;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

import share.com.ebj.R;
import share.com.ebj.SingleUser.UserSingleton;

public class InitActivity extends Application {
    private String TAG = "crazyK";
    private ImageLoaderConfiguration config;
    public static DbManager.DaoConfig daoConfig;
    @Override
    public void onCreate() {
        super.onCreate();
        /**初始化xUtils*/
        x.Ext.init(this);
        /**初始化imageloader*/
        initImageLoader(this);
        /**初始化xUtils 的DaoConfig*/
        initDaoConfig();
        /**判断用户是否登录*/
        SharedPreferences loginSP = getSharedPreferences("user_id", MODE_PRIVATE);
        int loginSP_user_id = loginSP.getInt("user_id", -1);
        if(loginSP_user_id == -1){
            Log.i(TAG, "未登录");
        }else {
            DbManager dbManager = x.getDb(daoConfig);
            try {
                List<DbModel> dbModelAll = dbManager.findDbModelAll(new SqlInfo("select user_id,name,pwd,self_sign,icon,goods_id from user where user_id = '"+loginSP_user_id+"'"));
                Log.i(TAG, "InitActivity --> onCreate() -->执行完findDbModelAll");
                int user_id = dbModelAll.get(0).getInt("user_id");
                String name = dbModelAll.get(0).getString("name");
                String pwd = dbModelAll.get(0).getString("pwd");
                String self_sign = dbModelAll.get(0).getString("self_sign");
                String icon = dbModelAll.get(0).getString("icon");
                String goods_id = dbModelAll.get(0).getString("goods_id");
                UserSingleton userSingleton = UserSingleton.getInstance();
                userSingleton.updateUser(user_id,name,pwd,self_sign,icon,goods_id);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }

    }

    /**初始化imageloader*/
    public void initImageLoader(Context context) {
        //File cacheDir = StorageUtils.getCacheDirectory(context);
        config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(1000)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                        .cacheInMemory(true)//开启内存缓存
                        .cacheOnDisk(true)//开启磁盘缓存
                        .showImageOnLoading(R.mipmap.ic_launcher)//加载过程中显示的图片
                        .showImageOnFail(R.mipmap.ic_launcher)//加载失败显示的图片
                        .build()) // default
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**初始化xUtils 的DaoConfig*/
    public void initDaoConfig(){
        daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName("team_project")
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {
                        Log.i(TAG, "打开表： "+table.getName());
                    }
                })
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        Log.i(TAG, "打开数据库");
                    }
                });
    }
}
