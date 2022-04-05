package com.example.springbootwithes.service;

import com.alibaba.fastjson.JSON;
import com.example.springbootwithes.config.EsClientConfig;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * EsContentService
 * es内容的操作类
 * @author Lee
 * @date 2022/4/5
 */
@Service
@Slf4j
public class EsContentService {

    @Resource
    public EsClientConfig esClientConfig;

    /**
     * 获取查询到的 内容和数据
     * @param index 索引的名称
     * @param paramMap 传入的参数
     * @return 如果出现异常，那么可能返回为null
     */
    public String search(String index, HashMap<String, String> paramMap) {
        String res = null;
        SearchRequest searchRequest = new SearchRequest(index);
        // 构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "lee");
        // 如果要用模糊查询的话，就用下面的这种方式
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "lee");
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(10, TimeUnit.SECONDS));

        // 执行下查询
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = esClientConfig.restHighLevelClient().search(searchRequest, RequestOptions.DEFAULT);
            res = JSON.toJSONString(searchResponse.getHits());
            SearchHit[] hits = searchResponse.getHits().getHits();
            Arrays.stream(hits).forEach(hit -> {
                System.out.println(hit.getSourceAsMap());
            });
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return res;
    }



}
