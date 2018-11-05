package com.zst.ynh.bean;

import java.util.List;

public class PersonInfoBean {

        /**
         * item : {"id":411349,"name":"李志鹏","id_number":"41092219921118583X","degrees":0,"marriage":0,"address":"","live_period":0,"address_distinct":"","longitude":"","latitude":"","real_verify_status":1,"face_recognition_picture":"https://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/jsqb/face_recognition/411349/B2A954F4A5BA9.jpg","id_number_z_picture":"https://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/jsqb/idcard/411349/1FBE6CD19C539.jpg","id_number_f_picture":"https://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/jsqb/idcard/411349/F6BB224318C7C.jpg","degrees_all":[{"degrees":4,"name":"大专"},{"degrees":5,"name":"中专"},{"degrees":3,"name":"本科"},{"degrees":2,"name":"硕士及以上"},{"degrees":6,"name":"高中及以下"}],"marriage_all":[{"marriage":1,"name":"未婚"},{"marriage":2,"name":"已婚未育"},{"marriage":3,"name":"已婚已育"},{"marriage":4,"name":"离异"},{"marriage":100,"name":"其他"}],"live_time_type_all":[{"live_time_type":1,"name":"半年以内"},{"live_time_type":2,"name":"半年到一年"},{"live_time_type":3,"name":"一年以上"}],"can_upload_img":0}
         */

        public ItemBean item;


        public static class ItemBean {
            /**
             * id : 411349
             * name : 李志鹏
             * id_number : 41092219921118583X
             * degrees : 0
             * marriage : 0
             * address :
             * live_period : 0
             * address_distinct :
             * longitude :
             * latitude :
             * real_verify_status : 1
             * face_recognition_picture : https://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/jsqb/face_recognition/411349/B2A954F4A5BA9.jpg
             * id_number_z_picture : https://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/jsqb/idcard/411349/1FBE6CD19C539.jpg
             * id_number_f_picture : https://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/jsqb/idcard/411349/F6BB224318C7C.jpg
             * degrees_all : [{"degrees":4,"name":"大专"},{"degrees":5,"name":"中专"},{"degrees":3,"name":"本科"},{"degrees":2,"name":"硕士及以上"},{"degrees":6,"name":"高中及以下"}]
             * marriage_all : [{"marriage":1,"name":"未婚"},{"marriage":2,"name":"已婚未育"},{"marriage":3,"name":"已婚已育"},{"marriage":4,"name":"离异"},{"marriage":100,"name":"其他"}]
             * live_time_type_all : [{"live_time_type":1,"name":"半年以内"},{"live_time_type":2,"name":"半年到一年"},{"live_time_type":3,"name":"一年以上"}]
             * can_upload_img : 0
             */

            public int id;
            public String name;
            public String id_number;
            public int degrees;
            public int marriage;
            public String address;
            public int live_period;
            public String address_distinct;
            public String longitude;
            public String latitude;
            public int real_verify_status;
            public String face_recognition_picture;
            public String id_number_z_picture;
            public String id_number_f_picture;
            public int can_upload_img;
            public List<DegreesAllBean> degrees_all;
            public List<MarriageAllBean> marriage_all;
            public List<LiveTimeTypeAllBean> live_time_type_all;


            public static class DegreesAllBean {
                /**
                 * degrees : 4
                 * name : 大专
                 */

                public int degrees;
                public String name;

                public int getDegrees() {
                    return degrees;
                }

                public void setDegrees(int degrees) {
                    this.degrees = degrees;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class MarriageAllBean {
                /**
                 * marriage : 1
                 * name : 未婚
                 */

                public int marriage;
                public String name;

                public int getMarriage() {
                    return marriage;
                }

                public void setMarriage(int marriage) {
                    this.marriage = marriage;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class LiveTimeTypeAllBean {
                /**
                 * live_time_type : 1
                 * name : 半年以内
                 */

                public int live_time_type;
                public String name;

            }
        }
    }
