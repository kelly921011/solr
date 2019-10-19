package com.example.solr.dao;

import com.example.solr.entity.Mobile_choiceness;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChsDao {
    /**
     * 查询精选案例
     * @return
     */
    public List<Mobile_choiceness> ChsList(String chs_title,String release_time);
}
