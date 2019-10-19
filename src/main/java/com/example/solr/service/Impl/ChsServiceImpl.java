package com.example.solr.service.Impl;

import com.example.solr.dao.ChsDao;
import com.example.solr.entity.Mobile_choiceness;
import com.example.solr.service.ChsService;
import com.example.solr.util.SearchResult;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChsServiceImpl implements ChsService {
    @Autowired
    private ChsDao chsDao;

    @Autowired
    private SolrClient solrClient;

    /**
     * solr 条件查询
     * @param queryString 查询条件
     * @param page 页码
     * @param rows 页长
     * @return
     * @throws Exception
     */
    public SearchResult searchItem(String queryString, Integer page, Integer rows) throws Exception {
        //解决乱码
        //queryString = new String(queryString.getBytes("ISO8859-1"), "UTF-8");
        //创建一个查询对象
        SolrQuery solrQuery = new SolrQuery();
        System.out.println("索引条件："+queryString);
        //查询条件
        if(queryString == null || queryString.equals("")){
            queryString = "*:*";
        }
        solrQuery.setQuery(queryString);

        //设置默认搜索域
        solrQuery.set("df", "ik_keywords");
        //分页条件
        if (page == null) {
            page = 0;
        }
        solrQuery.setStart(page);
        solrQuery.setRows(rows);
        //高亮显示
        /*
        solrQuery.set("hl",true);
        //设置高亮域(设置的域必须在查询条件中存在)
        solrQuery.set("h1.fl","chs_title");
        //前缀
        solrQuery.set("hl.simple.pre","<em style='color:red'>");
        //后缀
        solrQuery.set("hl.simple.post","</em>");*/

        solrQuery.setHighlight(true);
        //设置高亮显示的域
        solrQuery.addHighlightField("chs_title");
        //高亮显示前缀
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        //后缀
        solrQuery.setHighlightSimplePost("</em>");
        System.out.println("solrQuery:"+solrQuery);
        //执行查询
        SearchResult result = searchItemDao(solrQuery);
        //计算分页
        Long recordCount = result.getRecordCount();
        int pageCount = (int) (recordCount / rows);
        if (recordCount % rows > 0) {
            pageCount++;
        }
        result.setPageCount(pageCount);
        result.setCurPage(page);

        return result;
    }
    //执行查询
    public SearchResult searchItemDao(SolrQuery solrQuery) throws Exception {

        SearchResult searchResult = new SearchResult();
        List<Mobile_choiceness> itemList = new ArrayList<Mobile_choiceness>();
        //执行查询
        QueryResponse response = solrClient.query(solrQuery);
        System.out.println(response);
        //取查询结果
        SolrDocumentList solrDocumentList = response.getResults();
        //取查询结果总数量
        searchResult.setRecordCount(solrDocumentList.getNumFound());
        System.out.println("总记录数："+solrDocumentList.getNumFound());

        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        for(SolrDocument sd: solrDocumentList) {
            Mobile_choiceness oe = new Mobile_choiceness();
            oe.setChs_id(Integer.valueOf(sd.get("id").toString()));
            //oe.setChs_title(sd.get("chs_title").toString());
            //取高亮显示
            List<String> list = highlighting.get(sd.get("id")).get("chs_title");
            String title = "";
            if (null != list && list.size()>0) {
                title = list.get(0);
            } else {
                title = sd.get("chs_title").toString();
            }
            oe.setChs_title(title);

            List<String> author_list=highlighting.get(sd.get("id")).get("chs_author");
            String author="";
            if (null != author_list && author_list.size()>0) {
                author = author_list.get(0);
            } else {
                author = sd.get("chs_author").toString();
            }
            oe.setChs_author(author);

            List<String> content_list=highlighting.get(sd.get("id")).get("content");
            String content="";
            if (null != content_list && content_list.size()>0) {
                content = content_list.get(0);
            } else {
                content = sd.get("content").toString();
            }
            oe.setContent(content);
            oe.setLike_number(Integer.valueOf(sd.get("like_number").toString()));
            oe.setRelease_time(sd.get("release_time").toString());
            oe.setStatus(Integer.valueOf(sd.get("status").toString()));
            oe.setViewed(Integer.valueOf(sd.get("viewed").toString()));
            itemList.add(oe);
        }
        searchResult.setItemList(itemList);
        return searchResult;
    }

    //将所有数据导入到solr中
    public String importItemToIndex() throws Exception {
        //查询商品列表
        List<Mobile_choiceness> itemList = chsDao.ChsList(null,null);
        //将商品列表导入solr
        for (Mobile_choiceness item : itemList) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", item.getChs_id());
            document.addField("release_time", item.getRelease_time());
            document.addField("viewed", item.getViewed());
            document.addField("content", item.getContent());
            document.addField("like_number", item.getLike_number());
            document.addField("chs_title", item.getChs_title());
            document.addField("chs_author", item.getChs_author());
            document.addField("status", item.getStatus());
            //将文档写入索引库
            solrClient.add(document);
        }
        //提交修改
        solrClient.commit();
        return "success";
    }
}
