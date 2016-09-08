package share.com.ebj.leadFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import share.com.ebj.R;
import share.com.ebj.adapter.GouWuCheListViewAdapter;

/**
 * Created by Administrator on 2016/9/8.
 */
public class CarFragment extends Fragment implements View.OnClickListener {
    private ImageView gouwuche_back_img;
    private Button gouwucge_pay_button;
    private ListView gouwuche_listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_fragment, container, false);
        gouwuche_back_img = (ImageView) view.findViewById(R.id.gouwuche_back_img);//返回按钮
        gouwucge_pay_button = (Button) view.findViewById(R.id.gouwucge_pay_button);
        gouwuche_listview = (ListView) view.findViewById(R.id.gouwuche_listview);
        gouwuche_listview.setAdapter(new GouWuCheListViewAdapter(getActivity()));
        gouwuche_back_img.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gouwuche_back_img:
                getActivity().finish();
                break;
            case R.id.gouwucge_pay_button:
                break;

        }
    }
}