package share.com.ebj.JavaBean;

/**
 * Created by Administrator on 2016/9/16.
 */
public class GoodsBean_For_Car_ListView {
    private int goods_id;
    private String name;
    private String icon;
    private String size;
    private String prize;

    private boolean isCheck = false;
    private int et_count = 1;
    private int position;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean check) {
        isCheck = check;
    }

    public int getEt_count() {
        return et_count;
    }

    public void setEt_count(int et_count) {
        this.et_count = et_count;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
