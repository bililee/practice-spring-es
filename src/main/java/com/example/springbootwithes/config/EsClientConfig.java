package com.example.springbootwithes.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * EsClientConfig
 *
 * @author Lee
 * @date 2022/4/5
 */
@Configuration
public class EsClientConfig {


    /**
     * 连接Es的客户端配置类
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient restHighLevelClient;
        restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("127.0.0.1", 9200, "http"))
        );
        return restHighLevelClient;
    }
}
