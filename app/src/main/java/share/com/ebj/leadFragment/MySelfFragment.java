package share.com.ebj.leadFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import share.com.ebj.R;

/**
 * Created by Administrator on 2016/9/8.
 */
public class MySelfFragment extends Fragment{
    private String TAG = "crazyK";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.myself_fragment,container,false);
        Intent intentFromMain = getActivity().getIntent();
        int user_id = intentFromMain.getIntExtra("user_id", -1);
        Log.i(TAG, ""+user_id);
        return view;
    }
}
