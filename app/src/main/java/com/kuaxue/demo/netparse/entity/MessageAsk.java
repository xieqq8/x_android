/**
 * copyright(C)2014-
 */
package com.kuaxue.demo.netparse.entity;

import java.io.Serializable;

/**
 * 消息实体类
 * @author
 * @code: create：15-9-6下午2:40
 * class:
 */
public class MessageAsk implements Serializable {
    private String id;              // id
    private String imageUrl;        // ccid  -->根据这个ID去取实际的CCID和题文
    private String username;        // showTitle
    private String showTitle;   // showTitle
    private String showcontent;         // showContent
    private String contentTime;     // pushTime
    // 是否己读 false未读，true己读
    private boolean bViewed;         // viewed

    private int messageTypeA=0;     // 1 系统消息 2 答疑消息
    private String messageTypeB;     // video 视频 // question 做题  // 一般

    private String remarks="";

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // 是否己读 false未读，true己读
    public boolean getbViewed() {
        return bViewed;
    }

    public void setbViewed(boolean bViewed) {
        this.bViewed = bViewed;
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    private String questionNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getShowcontent() {
        return showcontent;
    }

    public void setShowcontent(String showcontent) {
        this.showcontent = showcontent;
    }

    public boolean isbViewed() {
        return bViewed;
    }

    public int getMessageTypeA() {
        return messageTypeA;
    }

    public void setMessageTypeA(int messageTypeA) {
        this.messageTypeA = messageTypeA;
    }

    public String getMessageTypeB() {
        return messageTypeB;
    }

    public void setMessageTypeB(String messageTypeB) {
        this.messageTypeB = messageTypeB;
    }

    public String getContentTime() {
        return contentTime;
    }

    public void setContentTime(String contentTime) {
        this.contentTime = contentTime;
    }
}
