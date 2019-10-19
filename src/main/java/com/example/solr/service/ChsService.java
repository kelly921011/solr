package com.example.solr.service;

import com.example.solr.util.SearchResult;


public interface ChsService {
    /**
     * solr 条件查询
     * @param queryString 查询条件
     * @param page 页码
     * @param rows 页长
     * @return
     * @throws Exception
     */
    SearchResult searchItem(String queryString, Integer page, Integer rows) throws Exception;

    /**
     *
     * @return
     * @throws Exception
     */
    public String importItemToIndex() throws Exception;
}
