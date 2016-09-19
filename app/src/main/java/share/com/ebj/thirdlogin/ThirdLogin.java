package share.com.ebj.thirdlogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2016/9/18.
 */
public class ThirdLogin {
    public String t_name = null;
    public String t_head = null;
    public String t_id = null;
    public String login_tag;
    public Context context;
    public Platform platform;

    public void t_login(final Context context, String login_tag) {
        ShareSDK.initSDK(context);
        platform = ShareSDK.getPlatform(login_tag);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                t_name = platform.getDb().getUserName();
                t_id = platform.getDb().getUserId();
                t_head = platform.getDb().getUserIcon();
                SharedPreferences loginSP = context.getSharedPreferences("user_id", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginSP.edit();
                editor.putInt("user_id", Integer.parseInt(t_id));
                editor.apply();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.e("登录情况","登录失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        if (platform.isAuthValid()) {
            return;
        }
        platform.SSOSetting(true);
        platform.showUser(null);
    }

    public void t_logout() {
        platform.removeAccount(false);
    }

}
