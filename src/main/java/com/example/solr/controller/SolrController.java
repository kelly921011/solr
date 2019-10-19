package com.example.solr.controller;

import com.example.solr.service.ChsService;
import com.example.solr.util.SearchResult;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SolrController {
    @Autowired
    ChsService chsService;
    @Autowired
    private SolrClient solrClient;

    //高亮查询 ，访问路径： localhost:8769/query?q=发
    @RequestMapping("/query")
    public SearchResult search(@RequestParam(value = "q") String queryString,
                               @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "rows", defaultValue = "60") Integer rows) throws Exception {
        return chsService.searchItem(queryString, page, rows);
    }

    //添加数据到solr
    @RequestMapping("/add")
    public String addSolr() throws Exception {
        return chsService.importItemToIndex();
    }
    /***
     * 清空所有数据
     */
    @RequestMapping("/deleteall")
    public String DeleteAll() throws Exception {
        // 清空所有数据
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
        return "solr所有数据已清除";
    }

    /**
     * 新增/修改 索引
     * 当 id 存在的时候, 此方法是修改(当然, 我这里用的 uuid, 不会存在的), 如果 id 不存在, 则是新增
     * @return
     */
    @RequestMapping("/update")
    public String update() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", 1);
            document.addField("release_time", "2019-09-27 17:28:10");
            document.addField("viewed", 10000);
            document.addField("content", "solr修改测试！");
            document.addField("like_number",8888);
            document.addField("chs_title", "solr修改测试！");
            document.addField("chs_author", "solr");
            document.addField("status", 1);
            /* 如果spring.data.solr.host 里面配置到 core了, 那么这里就不需要传 collection1 这个参数
             * 下面都是一样的
             */
            solrClient.add(document);
            //solrClient.commit("collection1");
            solrClient.commit();
            return "修改成功！";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
    /**
     * 根据id删除索引
     * @param id
     * @return
     */
    @RequestMapping("/deleteID")
    public String delete(String  id)  {
        try {
            solrClient.deleteById(id);
            solrClient.commit();
            return id+"删除成功！";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
}
