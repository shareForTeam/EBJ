package share.com.ebj.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import share.com.ebj.R;
import share.com.ebj.SingleUser.UserSingleton;
import share.com.ebj.Utils.DBOperation;

/**
 * Created by Administrator on 2016/9/5.
 * <p/>
 * 个人中心主函数
 */

public class PersonSettingActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "crazyK";
    private ImageView person_setting_back;
    private LinearLayout nicheng_button, sex_button, gexingqianming_button, changebg_button, erweima_button, change_passward_button;
    private CircleImageView circleImageView;
    private final int REQUEST_CODE = 0;
    private final int RESULT_CODE = 0;
    private int user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_setting);
        init();
        click();

    }

    @Override
    protected void onStart() {
        super.onStart();
        isLogin();
    }

    /**
     * 根据登录信息设置相应控件
     */
    public void isLogin() {
        SharedPreferences login_SP = getSharedPreferences("user_id", MODE_PRIVATE);
        user_id = login_SP.getInt("user_id", -1);
        if (user_id != -1) {
            UserSingleton userSingleton = UserSingleton.getInstance();
            String icon = userSingleton.getIcon();
//            String self_sign = userSingleton.getSelf_sign();
//            String name = userSingleton.getName();
            if (icon != null) {
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(icon, circleImageView);
            }
        } else {
            Toast.makeText(PersonSettingActivity.this, "逻辑发生错误，此界面必须为登录状态下才能进入", Toast.LENGTH_SHORT).show();
        }
    }

    /***
     * 控件的初始化
     */
    private void init() {
        person_setting_back = (ImageView) findViewById(R.id.person_setting_back);//返回键
        nicheng_button = (LinearLayout) findViewById(R.id.nicheng_button);//昵称设置
        sex_button = (LinearLayout) findViewById(R.id.sex_button);//性别设置
        gexingqianming_button = (LinearLayout) findViewById(R.id.gexingqianming_button);//个性签名设置
        changebg_button = (LinearLayout) findViewById(R.id.changebg_button);//背景更改设置
        erweima_button = (LinearLayout) findViewById(R.id.erweima_button);//二维码设置
        change_passward_button = (LinearLayout) findViewById(R.id.change_passward_button);//更改密码设置
        circleImageView = (CircleImageView) findViewById(R.id.circleImageView);
    }

    /***
     * 点击事件的设置
     */
    private void click() {
        person_setting_back.setOnClickListener(this);
        nicheng_button.setOnClickListener(this);
        sex_button.setOnClickListener(this);
        gexingqianming_button.setOnClickListener(this);
        changebg_button.setOnClickListener(this);
        erweima_button.setOnClickListener(this);
        change_passward_button.setOnClickListener(this);
        circleImageView.setOnClickListener(this);
    }


    /***
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回按钮
            case R.id.person_setting_back:
                finish();
                break;
            //昵称的设置
            case R.id.nicheng_button:
                Toast.makeText(getApplicationContext(), "昵称设置", Toast.LENGTH_SHORT).show();
                break;
            //性别设置
            case R.id.sex_button:
                Toast.makeText(getApplicationContext(), "性别设置", Toast.LENGTH_SHORT).show();
                break;
            //个性签名的设置
            case R.id.gexingqianming_button:
                Toast.makeText(getApplicationContext(), "个性签名的设置", Toast.LENGTH_SHORT).show();
                break;
            //背景更改的设置
            case R.id.changebg_button:
                Toast.makeText(getApplicationContext(), "背景更改的设置", Toast.LENGTH_SHORT).show();
                break;
            //二维码的设置
            case R.id.erweima_button:
                Toast.makeText(getApplicationContext(), "二维码的设置", Toast.LENGTH_SHORT).show();
                break;
            //密码更改的设置
            case R.id.change_passward_button:
                Toast.makeText(getApplicationContext(), "密码更改的设置", Toast.LENGTH_SHORT).show();
                break;
            //头像的设置
            case R.id.circleImageView:
                startSystemImage();
                break;

        }

    }

    /**
     * 调用系统相册,向服务器上传图片，服务器返回服务器图片地址，更新UserSingleton、本地数据库信息
     */
    public void startSystemImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
//            currtView.setTag(""+imagePath);

//            showImage(imagePath);

            String url = "http://172.18.4.18:8080/EBJ_Project/icon.do?type=android&query=upload&user_id="+this.user_id;
            RequestParams params = new RequestParams(url);
            params.addBodyParameter("file",new File(imagePath));
            params.setMultipart(true);
            x.http().request(HttpMethod.POST,params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String download_icon_url) {
                    // TODO: 2016/9/15  服务器 Column 'user_id' not found.
                    Log.i(TAG, "download_icon_url: "+download_icon_url);
                    if(download_icon_url != null && !download_icon_url.equals("上传文件失败")){
                        /**更新本地数据库*/
                        DBOperation dbOperation = new DBOperation();
                        Log.i(TAG, "user_id: "+user_id);
                        boolean updateIcon = dbOperation.updateIcon(user_id, download_icon_url);
                        if(updateIcon){
                            /**开始从服务器下载头像*/
                            ImageLoader imageLoader = ImageLoader.getInstance();
                            imageLoader.displayImage(download_icon_url,circleImageView);
                            /**更新UserSingleton*/
                            UserSingleton.getInstance().setIcon(download_icon_url);
                        }
                    }else{
                        Toast.makeText(PersonSettingActivity.this, "服务器上传头像错误，请联系官方客服", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.i(TAG, "onError: "+isOnCallback);
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
//            Bitmap touxiang = BitmapFactory.decodeFile(imagePath);
//            circleImageView.setImageBitmap(touxiang);
//            Intent intent_Result = new Intent();
//            intent_Result.putExtra("touxiang_path",imagePath);
//            setResult(RESULT_CODE,intent_Result);
            c.close();
        }
    }

}