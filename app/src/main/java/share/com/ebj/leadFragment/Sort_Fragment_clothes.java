package share.com.ebj.leadFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import share.com.ebj.R;
import share.com.ebj.recycleview.RecycleView_Adapter;

/**
 * Created by Administrator on 2016/9/7.
 */

public class Sort_Fragment_clothes extends Fragment {
    private RecyclerView rv_clothes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_fragment_clothes,container,false);
        rv_clothes = (RecyclerView) view.findViewById(R.id.id_fragment_clothes_rv);
        rv_clothes.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rv_clothes.setAdapter(new RecycleView_Adapter(getActivity()));

        return view;
    }
}
