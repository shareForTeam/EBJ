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

import de.hdodenhof.circleimageview.CircleImageView;
import share.com.ebj.R;
import share.com.ebj.SingleUser.UserSingleton;

/**
 * Created by Administrator on 2016/9/5.
 *
 * 个人中心主函数
 */

public class PersonSettingActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView person_setting_back;
    private LinearLayout nicheng_button,sex_button,gexingqianming_button,changebg_button,erweima_button,change_passward_button;
    private CircleImageView circleImageView;
    private final int REQUEST_CODE = 0;
    private final int RESULT_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_setting);
        init();
        click();




    }
    /** 根据登录信息设置相应控件*/
    public void isLogin(){
        SharedPreferences login_SP = getSharedPreferences("user_id", MODE_PRIVATE);
        int user_id = login_SP.getInt("user_id", -1);
        if(user_id != -1){
            UserSingleton userSingleton = UserSingleton.getInstance();
            String icon = userSingleton.getIcon();
            String self_sign = userSingleton.getSelf_sign();
            String name = userSingleton.getName();

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(icon,circleImageView);

        }else {

        }
    }

    /***
     * 控件的初始化
     */
    private void init(){
        person_setting_back= (ImageView) findViewById(R.id.person_setting_back);//返回键
        nicheng_button= (LinearLayout) findViewById(R.id.nicheng_button);//昵称设置
        sex_button= (LinearLayout) findViewById(R.id.sex_button);//性别设置
        gexingqianming_button= (LinearLayout) findViewById(R.id.gexingqianming_button);//个性签名设置
        changebg_button= (LinearLayout) findViewById(R.id.changebg_button);//背景更改设置
        erweima_button= (LinearLayout) findViewById(R.id.erweima_button);//二维码设置
        change_passward_button= (LinearLayout) findViewById(R.id.change_passward_button);//更改密码设置
        circleImageView= (CircleImageView) findViewById(R.id.circleImageView);
    }

    /***
     * 点击事件的设置
     */
    private void click(){
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
     *
     *  点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //返回按钮
            case R.id.person_setting_back:
                finish();
                break;
            //昵称的设置
            case R.id.nicheng_button:
                Toast.makeText(getApplicationContext(),"昵称设置",Toast.LENGTH_SHORT).show();
                break;
            //性别设置
            case R.id.sex_button:
                Toast.makeText(getApplicationContext(),"性别设置",Toast.LENGTH_SHORT).show();
                break;
            //个性签名的设置
            case R.id.gexingqianming_button:
                Toast.makeText(getApplicationContext(),"个性签名的设置",Toast.LENGTH_SHORT).show();
                break;
            //背景更改的设置
            case R.id.changebg_button:
                Toast.makeText(getApplicationContext(),"背景更改的设置",Toast.LENGTH_SHORT).show();
                break;
            //二维码的设置
            case R.id.erweima_button:
                Toast.makeText(getApplicationContext(),"二维码的设置",Toast.LENGTH_SHORT).show();
                break;
            //密码更改的设置
            case R.id.change_passward_button:
                Toast.makeText(getApplicationContext(),"密码更改的设置",Toast.LENGTH_SHORT).show();
                break;
            //头像的设置
            case R.id.circleImageView:
                startSystemImage();
                break;

        }

    }

    /**调用系统相册*/
    public void  startSystemImage(){
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
            /**设置选中的图片*/
//            showImage(imagePath);
            // TODO: 2016/9/13 向服务器上传图片，服务器返回服务器图片地址，更新UserSingleton、本地数据库信息
            Bitmap touxiang = BitmapFactory.decodeFile(imagePath);
            circleImageView.setImageBitmap(touxiang);
            Intent intent_Result = new Intent();
            intent_Result.putExtra("touxiang_path",imagePath);
            setResult(RESULT_CODE,intent_Result);
            c.close();
        }
    }

}