package share.com.ebj.jsonStr;

import java.util.List;

/**
 * Created by Administrator on 2016/9/11.
 */
public class UserJson {

    /**
     * error_code : 200
     * reason : exit
     * list : [{"user_id":1,"name":"hkc","pwd":"123","self_sign":"个性签名","icon":"http","goods_id":"25"}]
     */

    private int error_code;
    private String reason;
    /**
     * user_id : 1
     * name : hkc
     * pwd : 123
     * self_sign : 个性签名
     * icon : http
     * goods_id : 25
     */

    private List<ListBean> list;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int user_id;
        private String name;
        private String pwd;
        private String self_sign;
        private String icon;
        private String goods_id;

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

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }
    }
}
