package com.example.solr.entity;

import java.io.Serializable;

/**
 * 精选案例表
 */
public class Mobile_choiceness implements Serializable {
    private Integer chs_id;   //精选案例主键ID
    private String  release_time;   //发布时间
    private Integer viewed;   //浏览次数
    private String content;   //正文
    private Integer like_number;   //点赞数量
    private String chs_title;   //标题
    private String chs_author;   //作者
    private Integer status;   //状态（1：发布；2：未发布）


    public Mobile_choiceness() {
    }

    public Mobile_choiceness(Integer chs_id, String release_time, Integer viewed, String content, Integer like_number, String chs_title, String chs_author, Integer status) {
        this.chs_id = chs_id;
        this.release_time = release_time;
        this.viewed = viewed;
        this.content = content;
        this.like_number = like_number;
        this.chs_title = chs_title;
        this.chs_author = chs_author;
        this.status = status;
    }

    public Integer getChs_id() {
        return chs_id;
    }

    public void setChs_id(Integer chs_id) {
        this.chs_id = chs_id;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public Integer getViewed() {
        return viewed;
    }

    public void setViewed(Integer viewed) {
        this.viewed = viewed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLike_number() {
        return like_number;
    }

    public void setLike_number(Integer like_number) {
        this.like_number = like_number;
    }

    public String getChs_title() {
        return chs_title;
    }

    public void setChs_title(String chs_title) {
        this.chs_title = chs_title;
    }

    public String getChs_author() {
        return chs_author;
    }

    public void setChs_author(String chs_author) {
        this.chs_author = chs_author;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
