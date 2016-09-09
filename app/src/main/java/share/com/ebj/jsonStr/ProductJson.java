package share.com.ebj.jsonStr;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public class ProductJson {


    /**
     * error_code : 200
     * reason : Success
     * list : [{"goods_id":17,"goods_name":"leccalecca文艺怀旧格子睡裙","goods_price":"189","goods_icon":"http://172.18.4.18:8080/EBJ_Project/image/skirt/1-1.jpg;http://172.18.4.18:8080/EBJ_Project/image/skirt/1-2.jpg;http://172.18.4.18:8080/EBJ_Project/image/skirt/1-3.jpg","goods_description":"颜色: 紫色;性别: 女款;裙长: 中;袖长: 无袖;腰型: 正常;经典款型: A字","goods_size":"L","goods_washing":"涤纶/聚酯纤维: 涤纶类的服装可机洗，可手洗，可干洗，可用毛刷刷洗。不可曝晒，不宜烘干。熨烫温度不能超过110度，熨烫时一定要打蒸汽，不能干烫 。在日光下晾晒时，将里面朝外。"}]
     */

    private int error_code;
    private String reason;
    /**
     * goods_id : 17
     * goods_name : leccalecca文艺怀旧格子睡裙
     * goods_price : 189
     * goods_icon : http://172.18.4.18:8080/EBJ_Project/image/skirt/1-1.jpg;http://172.18.4.18:8080/EBJ_Project/image/skirt/1-2.jpg;http://172.18.4.18:8080/EBJ_Project/image/skirt/1-3.jpg
     * goods_description : 颜色: 紫色;性别: 女款;裙长: 中;袖长: 无袖;腰型: 正常;经典款型: A字
     * goods_size : L
     * goods_washing : 涤纶/聚酯纤维: 涤纶类的服装可机洗，可手洗，可干洗，可用毛刷刷洗。不可曝晒，不宜烘干。熨烫温度不能超过110度，熨烫时一定要打蒸汽，不能干烫 。在日光下晾晒时，将里面朝外。
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
        private int goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_icon;
        private String goods_description;
        private String goods_size;
        private String goods_washing;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_icon() {
            return goods_icon;
        }

        public void setGoods_icon(String goods_icon) {
            this.goods_icon = goods_icon;
        }

        public String getGoods_description() {
            return goods_description;
        }

        public void setGoods_description(String goods_description) {
            this.goods_description = goods_description;
        }

        public String getGoods_size() {
            return goods_size;
        }

        public void setGoods_size(String goods_size) {
            this.goods_size = goods_size;
        }

        public String getGoods_washing() {
            return goods_washing;
        }

        public void setGoods_washing(String goods_washing) {
            this.goods_washing = goods_washing;
        }
    }
}
