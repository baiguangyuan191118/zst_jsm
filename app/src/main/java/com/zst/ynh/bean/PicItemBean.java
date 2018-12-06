package com.zst.ynh.bean;


import java.util.List;

public class PicItemBean {
    public final static int Type_Uploaded = 3;//上传完成
    public final static int Type_Uploading = 4;//上传中
    public final static int Type_UploadFailed = 5;//上传失败
    /**
     * code : 0
     * message : 成功获取
     * data : {"item":{"type":"6","max_pictures":3,"title":"工作证照","notice":"请提供可以证明您在此公司工作的照片，如含本人照片的工牌照、名片、与公司Logo合影照等","data":[{"id":1070324,"pic_name":"3F04F02848F7F.jpg","type":6,"url":"http://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/jsqb/work_card/419630/3F04F02848F7F.jpg"}]}}
     */

    public DataBeanX data;


    public static class DataBeanX {
        /**
         * item : {"type":"6","max_pictures":3,"title":"工作证照","notice":"请提供可以证明您在此公司工作的照片，如含本人照片的工牌照、名片、与公司Logo合影照等","data":[{"id":1070324,"pic_name":"3F04F02848F7F.jpg","type":6,"url":"http://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/jsqb/work_card/419630/3F04F02848F7F.jpg"}]}
         */

        public ItemBean item;


        public static class ItemBean {
            /**
             * type : 6
             * max_pictures : 3
             * title : 工作证照
             * notice : 请提供可以证明您在此公司工作的照片，如含本人照片的工牌照、名片、与公司Logo合影照等
             * data : [{"id":1070324,"pic_name":"3F04F02848F7F.jpg","type":6,"url":"http://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/jsqb/work_card/419630/3F04F02848F7F.jpg"}]
             */

            public String type;
            public int max_pictures;
            public String title;
            public String notice;
            public List<DataBean> data;


            public static class DataBean {
                /**
                 * id : 1070324
                 * pic_name : 3F04F02848F7F.jpg
                 * type : 6
                 * url : http://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/jsqb/work_card/419630/3F04F02848F7F.jpg
                 */

                public int id;
                public String pic_name;
                public int type;
                public String url;

            }
        }
    }
}
