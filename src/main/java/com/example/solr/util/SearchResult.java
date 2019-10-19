package com.example.solr.util;

import com.example.solr.entity.Mobile_choiceness;

import java.util.List;

public class SearchResult {
    //总条数
    private Long recordCount;
    //数据集合
    private List<Mobile_choiceness> itemList;
    //总页数
    private Integer pageCount;
    //当前页
    private Integer curPage;

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public List<Mobile_choiceness> getItemList() {
        return itemList;
    }

    public void setItemList(List<Mobile_choiceness> itemList) {
        this.itemList = itemList;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }
}
