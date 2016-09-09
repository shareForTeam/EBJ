package share.com.ebj.Utils;

/**
 * Created by Administrator on 2016/9/7.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 用于将json返回的icon字符串，以分号split，组合成所需集合
 * */
public class IconStr_To_List {
    private String TAG = "crazyK";

    public List<String> getIconList(String jsonStr){
        String[] splitIconStr = jsonStr.split(";");
        ArrayList<String> iconList = new ArrayList<>();
        for (int i = 0; i < splitIconStr.length; i++) {
            iconList.add(splitIconStr[i]);
        }
        return iconList;

    }
}
