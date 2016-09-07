package share.com.ebj.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import share.com.ebj.R;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        imageView = (ImageView) findViewById(R.id.img);
        sharedPreferences = getSharedPreferences("SPFILE", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(1000);
        imageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /**判断是否为第一次登陆*/
                        try {
                            tag = sharedPreferences.getString("tag", null);
                            if (tag.equals("1")) {
                                Intent intent = new Intent();
                                intent.setClass(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (Exception e) {
                            Intent intent = new Intent();
                            intent.setClass(SplashActivity.this, LeaderActivity.class);
                            editor.putString("tag", "1");
                            editor.commit();
                            startActivity(intent);
                            finish();
                        }


                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
