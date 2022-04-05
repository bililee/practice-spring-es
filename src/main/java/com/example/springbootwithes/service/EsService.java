package com.example.springbootwithes.service;

import com.example.springbootwithes.config.EsClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * EsService
 * 这里主要是索引的操作类
 * es 的服务类
 * @author Lee
 * @date 2022/4/5
 */
@Service
@Slf4j
public class EsService {


    @Resource
    public EsClientConfig esClientConfig;

    /**
     * 判断ES中某个索引是否存在
     * @param indexName
     * @return
     */
    public boolean checkIndexExists(String indexName) {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        boolean exists = false;
        try {
            exists = esClientConfig.restHighLevelClient().indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        }
        return exists;
    }

    /**
     * 判断索引是否存在，因为存在多种情况，所以这里用int来进行标识
     * 0 标识创建成功
     * -1 标识中间出现了异常
     * 1 标识该索引已经存在了不用重复创建
     * @param indexName
     * @return
     */
    public int createIndex(String indexName) {
        if (checkIndexExists(indexName)) {
            return 1;
        }
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        try {
            AcknowledgedResponse acknowledgedResponse = esClientConfig.restHighLevelClient().indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
            return -1;
        }

        // 成功
        return 0;
    }

    /**
     * 删除索引
     * 如果本来就不存在这个索引，那么返回 1
     * 如果删除失败，返回 -1
     * @param indexName
     * @return
     */
    public int deleteIndex(String indexName) {
        if (!checkIndexExists(indexName)) {
            return 1;
        }
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        try {
            AcknowledgedResponse acknowledgedResponse = esClientConfig.restHighLevelClient().indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
            return -1;
        }
        return 0;
    }
}
