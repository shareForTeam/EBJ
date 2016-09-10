package share.com.ebj.JavaBean;

/**
 * Created by Administrator on 2016/9/10.
 */
public class UserLogin {
    private int user_id;
    private String name;
    private String pwd;

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
}
