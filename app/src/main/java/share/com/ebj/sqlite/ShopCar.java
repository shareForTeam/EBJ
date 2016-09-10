package share.com.ebj.sqlite;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/9/10.
 */
@Table(name = "shopcar")
public class ShopCar {
    @Column(name = "shopcar_id",isId = true ,autoGen = true)
    private int shopcar_id;
    @Column(name = "goods_id")
    private String goods_id;

    public ShopCar() {
    }

    public ShopCar(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getShopcar_id() {
        return shopcar_id;
    }

    public void setShopcar_id(int shopcar_id) {
        this.shopcar_id = shopcar_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }
}
