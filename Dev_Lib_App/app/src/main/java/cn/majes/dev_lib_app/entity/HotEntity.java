package cn.majes.dev_lib_app.entity;

import java.util.List;

/**
 * @author majes
 * @date 12/16/17.
 */

public class HotEntity{

    /**
     * error : false
     * results : [{"_id":"5a2f2486421aa90fe2f02cd2","createdAt":"2017-12-12T08:36:22.670Z","desc":"12-12","publishedAt":"2017-12-15T08:59:11.361Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171212083612_WvLcTr_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a2dd04e421aa90fe2f02ccc","createdAt":"2017-12-11T08:24:46.981Z","desc":"12-11","publishedAt":"2017-12-11T08:43:39.682Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171211082435_CCblJd_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a273d40421aa90fef203585","createdAt":"2017-12-06T08:43:44.386Z","desc":"12-6","publishedAt":"2017-12-06T08:49:34.303Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171206084331_wylXWG_misafighting_6_12_2017_8_43_16_390.jpeg","used":true,"who":"daimajia"},{"_id":"5a20ace0421aa90fe50c024c","createdAt":"2017-12-01T09:14:08.63Z","desc":"12-1","publishedAt":"2017-12-05T08:48:31.384Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171201091356_OPqmuO_kanna399_1_12_2017_9_13_42_126.jpeg","used":true,"who":"daimajia"},{"_id":"5a1ad043421aa90fe725366c","createdAt":"2017-11-26T22:31:31.363Z","desc":"11-26","publishedAt":"2017-11-30T13:11:10.665Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171126223118_jeCYtY_chayexiaoguo_apple_26_11_2017_22_30_59_409.jpeg","used":true,"who":"代码家"},{"_id":"5a16171d421aa90fef203553","createdAt":"2017-11-23T08:32:29.546Z","desc":"11-23","publishedAt":"2017-11-24T11:08:03.624Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171123083218_5mhRLg_sakura.gun_23_11_2017_8_32_9_312.jpeg","used":true,"who":"daimajia"},{"_id":"5a121895421aa90fe50c021e","createdAt":"2017-11-20T07:49:41.797Z","desc":"2017-11-20","publishedAt":"2017-11-20T12:42:06.454Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171120074925_ZXDh6l_joanne_722_20_11_2017_7_49_16_336.jpeg","used":true,"who":"daimajia"},{"_id":"5a0e4a0d421aa90fe7253643","createdAt":"2017-11-17T10:31:41.155Z","desc":"11-17","publishedAt":"2017-11-17T12:39:48.189Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-11-17-22794158_128707347832045_9158114204975104000_n.jpg","used":true,"who":"代码家"},{"_id":"5a0d0c97421aa90fe2f02c60","createdAt":"2017-11-16T11:57:11.4Z","desc":"11-16","publishedAt":"2017-11-16T12:01:05.619Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171116115656_vnsrab_Screenshot.jpeg","used":true,"who":"代码家"},{"_id":"5a0a5141421aa90fef203525","createdAt":"2017-11-14T10:13:21.137Z","desc":"11-14","publishedAt":"2017-11-14T10:43:36.180Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171114101305_NIAzCK_rakukoo_14_11_2017_10_12_58_703.jpeg","used":true,"who":"daimajia"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

//    @Override
//    public boolean isNull() {
//        return results == null || results.isEmpty();
//    }
//
//    @Override
//    public boolean isAuthError() {
//        return false;
//    }
//
//    @Override
//    public boolean isBizError() {
//        return false;
//    }
//
//    @Override
//    public String getErrorMsg() {
//        return null;
//    }

    public static class ResultsBean {
        /**
         * _id : 5a2f2486421aa90fe2f02cd2
         * createdAt : 2017-12-12T08:36:22.670Z
         * desc : 12-12
         * publishedAt : 2017-12-15T08:59:11.361Z
         * source : chrome
         * type : 福利
         * url : http://7xi8d6.com1.z0.glb.clouddn.com/20171212083612_WvLcTr_Screenshot.jpeg
         * used : true
         * who : daimajia
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
