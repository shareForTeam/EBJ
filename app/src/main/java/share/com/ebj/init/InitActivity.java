package share.com.ebj.init;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.xutils.x;

public class InitActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
