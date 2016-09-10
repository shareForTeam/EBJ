package share.com.ebj.sqlite;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/9/10.
 */
@Table(name = "user")
public class User_Info {
    @Column(name = "user_id", isId = true , autoGen = true)
    private int user_id;
    @Column(name = "name")
    private String name;
    @Column(name = "pwd")
    private String pwd;
    @Column(name = "self_sign")
    private String self_sign;
    @Column(name = "icon")
    private String icon;
    @Column(name = "shopcar_id")
    private int shopcar_id;

    public User_Info() {
    }

    public User_Info(String name, String pwd, String self_sign, String icon, int shopcar_id) {
        this.name = name;
        this.pwd = pwd;
        this.self_sign = self_sign;
        this.icon = icon;
        this.shopcar_id = shopcar_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSelf_sign() {
        return self_sign;
    }

    public void setSelf_sign(String self_sign) {
        this.self_sign = self_sign;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getShopcar_id() {
        return shopcar_id;
    }

    public void setShopcar_id(int shopcar_id) {
        this.shopcar_id = shopcar_id;
    }
}
