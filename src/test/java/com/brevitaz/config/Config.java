package com.brevitaz.config;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.allegro.tech.embeddedelasticsearch.EmbeddedElastic;
import pl.allegro.tech.embeddedelasticsearch.PopularProperties;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Configuration
public class Config
{
    private RestHighLevelClient client;
    private EmbeddedElastic embeddedElastic;

    @Value("${Host-Name}")
    private String hostName;

    @Value("${Port-Number}")
    private Integer portName;

    @Value("${Scheme}")
    private String scheme;

    @Value("${Cluster-Name}")
    private String clusterName;


    @Bean
    public RestHighLevelClient getClient() {

        if (client == null)
        {
            client = new RestHighLevelClient
                    (RestClient.builder(
                            new HttpHost(hostName, portName, scheme)));
            return client;
        }
        else
        {
            return client;
        }

    }

    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }

    @PostConstruct
    public void startES() throws Exception {
 /*Settings settings = Settings.builder()
 .put(PopularProperties.CLUSTER_NAME, clusterName)
 .build();*/
        try {
            embeddedElastic = EmbeddedElastic.builder()
                    .withElasticVersion("6.1.1")
// .withEsJavaOpts("-Xms256m -Xmx1g")
// .withSetting(PopularProperties.TRANSPORT_TCP_PORT, 9300)
                    .withSetting(PopularProperties.HTTP_PORT, portName)
                    .withSetting(PopularProperties.CLUSTER_NAME, clusterName)
                    .withStartTimeout(60, TimeUnit.SECONDS)
                    .build()
                    .start();
            System.out.println("Es started");
        }
    catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public Config() {
    }
}
