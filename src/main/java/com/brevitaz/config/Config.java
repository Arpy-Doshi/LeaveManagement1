package com.brevitaz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config
{
    private RestHighLevelClient client;
    private ObjectMapper objectMapper;

    @Value("${Host-Name}")
    private String hostName;

    @Value("${Port-Number}")
    private Integer portName;

    @Value("${Scheme}")
    private String scheme;


    @Bean
    public RestHighLevelClient getClient() {
        client = new RestHighLevelClient
                    (RestClient.builder(
                            new HttpHost(hostName, portName, scheme)));
            return client;
    }


    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }

    @Bean
    public ObjectMapper getObjectMapper() {
            objectMapper = new ObjectMapper();
            return objectMapper;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Config() {
    }
}
