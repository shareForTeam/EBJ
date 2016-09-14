package share.com.ebj.leadFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import share.com.ebj.Activity.PersonSettingActivity;
import share.com.ebj.R;

/**
 * Created by Administrator on 2016/9/8.
 */
public class MySelfFragment extends Fragment implements View.OnClickListener {
    private String TAG = "crazyK";
    private RelativeLayout rv_accountSetting;
    private CircleImageView civ_touxiang;
    private final int REQUEST_CODE = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO: 2016/9/13 判断是否登录，如果登录获取个人信息更新相关控件
        View view=inflater.inflate(R.layout.fragment_setting,container,false);
        initView(view);
        initListener();
        Intent intentFromMain = getActivity().getIntent();
        int user_id = intentFromMain.getIntExtra("user_id", -1);
        Log.i(TAG, ""+user_id);
        return view;
    }

    public void initView(View view){
        rv_accountSetting = (RelativeLayout) view.findViewById(R.id.fragment_setting_account_setting);
        civ_touxiang = (CircleImageView) view.findViewById(R.id.fragment_setting_touxiang);
    }

    public void initListener(){
        rv_accountSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /**账户设置*/
            case R.id.fragment_setting_account_setting:
                // TODO: 2016/9/13 判断是否登录
                Intent intent_MyselfToSettingCenter = new Intent(getActivity(), PersonSettingActivity.class);
                /**需要带回来头像bitmap，个性签名*/
                startActivityForResult(intent_MyselfToSettingCenter,REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 &&resultCode == 0){
            if(data != null){
                String touxiang_path = data.getStringExtra("touxiang_path");
                Bitmap touxiang = BitmapFactory.decodeFile(touxiang_path);
                civ_touxiang.setImageBitmap(touxiang);
            }

        }
    }
}
