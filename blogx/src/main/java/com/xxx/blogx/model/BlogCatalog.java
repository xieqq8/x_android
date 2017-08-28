package com.xxx.blogx.model;

import java.util.List;

/**
 * Created by kuaX on 2017/8/25.
 */

public class BlogCatalog {
    /**
     * success : true
     * code : null
     * msg : null
     * data : [{"id":"java","label":"Java","path":"0","order":10,"type":0,"style":null,"disabled":false,"childNodes":[],"url":"/catalog/Java","level":1},{"id":"neihan","label":"男人就要有内涵","path":"0","order":20,"type":0,"style":null,"disabled":false,"childNodes":[],"url":"","level":1},{"id":"spring","label":"spring","path":"0","order":10,"type":1,"style":null,"disabled":false,"childNodes":[],"url":"","level":1},{"id":"yunwei","label":"女人就该有韵味","path":"0","order":30,"type":0,"style":null,"disabled":false,"childNodes":[],"url":"","level":1}]
     {
     "success": true,
     "code": null,
     "msg": null,
     "data": [
     {
     "id": "java",
     "label": "Java",
     "path": "0",
     "order": 10,
     "type": 0,
     "style": null,
     "disabled": false,
     "childNodes": [],
     "url": "/catalog/Java",
     "level": 1
     },
     {
     "id": "neihan",
     "label": "男人就要有内涵",
     "path": "0",
     "order": 20,
     "type": 0,
     "style": null,
     "disabled": false,
     "childNodes": [],
     "url": "",
     "level": 1
     },
     {
     "id": "spring",
     "label": "spring",
     "path": "0",
     "order": 10,
     "type": 1,
     "style": null,
     "disabled": false,
     "childNodes": [],
     "url": "",
     "level": 1
     },
     {
     "id": "yunwei",
     "label": "女人就该有韵味",
     "path": "0",
     "order": 30,
     "type": 0,
     "style": null,
     "disabled": false,
     "childNodes": [],
     "url": "",
     "level": 1
     }
     ]
     }
     */

//    private boolean success;
//    private Object code;
//    private Object msg;
//    private List<DataBean> data;
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public Object getCode() {
//        return code;
//    }
//
//    public void setCode(Object code) {
//        this.code = code;
//    }
//
//    public Object getMsg() {
//        return msg;
//    }
//
//    public void setMsg(Object msg) {
//        this.msg = msg;
//    }

//    public List<DataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<DataBean> data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
        /**
         * id : java
         * label : Java
         * path : 0
         * order : 10
         * type : 0
         * style : null
         * disabled : false
         * childNodes : []
         * url : /catalog/Java
         * level : 1
         */

        private String id;
        private String label;
        private String path;
        private int order;
        private int type;
        private Object style;
        private boolean disabled;
        private String url;
        private int level;
        private List<?> childNodes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getStyle() {
            return style;
        }

        public void setStyle(Object style) {
            this.style = style;
        }

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public List<?> getChildNodes() {
            return childNodes;
        }

        public void setChildNodes(List<?> childNodes) {
            this.childNodes = childNodes;
        }
//    }

}
