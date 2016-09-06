package share.com.ebj.leadFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import share.com.ebj.R;


/**
 * Created by Administrator on 2016/8/30.
 */
public class One extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        return  view;
    }
}
