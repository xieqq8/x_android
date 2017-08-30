package com.xxx.blogx.model;

import java.util.List;

/**
 * Created by kuaX on 2017/8/28.
 */

public class BlogModel {

//    /**
//     * success : true
//     * code : null
//     * msg : null
//     * data : {"page":1,"size":5,"list":[{"title":"teacher kang 你有水吗","summary":"<p>公司新来了一位同事姓康，平时不太爱说话。一美女同事就逗他：\u201c康师傅，我能泡你吗？\u201d然后康师傅说了一句话，让全公司都嗨翻了。<\/p>\r\n","link":"/iblog/1502267983446","catalogDisplay":"男人就要有内涵","catalog":"neihan","publishTime":1502267983446},{"title":"first streak 还是红中","summary":"<p>夫妻给别人放了炮！夫妻二人合伙打麻将，商量好看妻子的眼色行事！玩牌中，妻子猛然劈开大腿，其夫见忙出八万！结果给别人放了炮！其夫不解！妻子大怒的喊道：\u201c我她妈要的是？？！\u201d<\/p>\r\n","link":"/iblog/1502267529698","catalogDisplay":"男人就要有内涵","catalog":"neihan","publishTime":1502267529698},{"title":"一去二三里，烟村四五家，亭台六七座，八九十枝花。","summary":"<p>上联：一男二女玩三P不知四廉五耻六目相对竟七上八下用九种姿势十分大胆求下联<\/p>\r\n<p>十室九贫筹得八两七钱六分五毫四厘尚且三心二意一等下流<\/p>\r\n","link":"/iblog/1502267157863","catalogDisplay":"男人就要有内涵","catalog":"neihan","publishTime":1502267219048},{"title":"ride tiger 难下_难下","summary":"<p>我一朋友男的，女朋友嫌他那方面不行，后来，女的在自己屁股上纹了一只小老虎，从此幸福快乐，至今我也想不明白<\/p>\r\n","link":"/iblog/1502266944075","catalogDisplay":"男人就要有内涵","catalog":"neihan","publishTime":1502267204196}],"hasNext":false}
//     */
//
//    private boolean success;
//    private Object code;
//    private Object msg;
//    private DataBean data;
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
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
    /**
     * page : 1
     * size : 5
     * list : [{"title":"teacher kang 你有水吗","summary":"<p>公司新来了一位同事姓康，平时不太爱说话。一美女同事就逗他：\u201c康师傅，我能泡你吗？\u201d然后康师傅说了一句话，让全公司都嗨翻了。<\/p>\r\n","link":"/iblog/1502267983446","catalogDisplay":"男人就要有内涵","catalog":"neihan","publishTime":1502267983446},{"title":"first streak 还是红中","summary":"<p>夫妻给别人放了炮！夫妻二人合伙打麻将，商量好看妻子的眼色行事！玩牌中，妻子猛然劈开大腿，其夫见忙出八万！结果给别人放了炮！其夫不解！妻子大怒的喊道：\u201c我她妈要的是？？！\u201d<\/p>\r\n","link":"/iblog/1502267529698","catalogDisplay":"男人就要有内涵","catalog":"neihan","publishTime":1502267529698},{"title":"一去二三里，烟村四五家，亭台六七座，八九十枝花。","summary":"<p>上联：一男二女玩三P不知四廉五耻六目相对竟七上八下用九种姿势十分大胆求下联<\/p>\r\n<p>十室九贫筹得八两七钱六分五毫四厘尚且三心二意一等下流<\/p>\r\n","link":"/iblog/1502267157863","catalogDisplay":"男人就要有内涵","catalog":"neihan","publishTime":1502267219048},{"title":"ride tiger 难下_难下","summary":"<p>我一朋友男的，女朋友嫌他那方面不行，后来，女的在自己屁股上纹了一只小老虎，从此幸福快乐，至今我也想不明白<\/p>\r\n","link":"/iblog/1502266944075","catalogDisplay":"男人就要有内涵","catalog":"neihan","publishTime":1502267204196}]
     * hasNext : false
     */

    private int page;
    private int size;
    private boolean hasNext;
    private List<ListBean> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * title : teacher kang 你有水吗
         * summary : <p>公司新来了一位同事姓康，平时不太爱说话。一美女同事就逗他：“康师傅，我能泡你吗？”然后康师傅说了一句话，让全公司都嗨翻了。</p>
         * <p>
         * link : /iblog/1502267983446
         * catalogDisplay : 男人就要有内涵
         * catalog : neihan
         * publishTime : 1502267983446
         */

        private String title;
        private String summary;
        private String link;
        private String catalogDisplay;
        private String catalog;
        private long publishTime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getCatalogDisplay() {
            return catalogDisplay;
        }

        public void setCatalogDisplay(String catalogDisplay) {
            this.catalogDisplay = catalogDisplay;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }
    }
//    }
}
