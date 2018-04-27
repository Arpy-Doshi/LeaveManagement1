package com.brevitaz.config;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class IndexInitializer
{
        @Autowired
        private RestHighLevelClient client;

        @Value("${Employee-Index-Name}")
        private String employeeIndex;

        @Value("${LeaveApplication-Index-Name}")
        private String leaveApplicationIndex;

        @Value("${LeavePolicy-Index-Name}")
        private String leavePolicyIndex;

        private String indexName;


        @PostConstruct
        public void doInitialSetup()
        {
                List<String> indexNames = new ArrayList<>();
                indexNames.add(employeeIndex);
                indexNames.add(leaveApplicationIndex);
                indexNames.add(leavePolicyIndex);

                System.out.println(indexNames);

                for (String indexName : indexNames) {
                        if (!isIndexExists(indexName))
                                createIndexIfNotExists(indexName);
                }
        }

        private void createIndexIfNotExists(String indexName)
        {
                try
                {
                        CreateIndexRequest request = new CreateIndexRequest(indexName);
                        CreateIndexResponse createIndexResponse = client.indices().create(request);

                        /*XContentBuilder mappings = null;
                        mappings = XContentFactory.jsonBuilder();

                        mappings.startObject().startObject("analysis").startObject("analyzer").startObject("default")
                                .field("type", "custom").field("tokenizer",
                                "whitespace")
                                .field("filter", new String[] { "snowball",
                                        "standard", "lowercase" }).endObject().endObject()
                                .endObject().endObject();


                        PutMappingRequestBuilder mappingRequest =
                                client.admin().indices().preparePutMapping("default");
                        mappingRequest.setSource(mappings);
                        PutMappingResponse mappingResponse =
                                mappingRequest.execute().actionGet();*/
                        boolean acknowledged = createIndexResponse.isAcknowledged();
                        System.out.println("IndexCreated!!!"+acknowledged);
                }
                catch (IOException e)
                {
                        e.printStackTrace();
                }

        }

        private boolean isIndexExists(String indexName)
        {
                try
                {
                        Response response;
                        response = client.getLowLevelClient().performRequest("HEAD", "/" + indexName);
                        int statusCode = response.getStatusLine().getStatusCode();
                        if(statusCode == 404)
                        {
                                System.out.println("=============================================================");
                                return false;
                        }
                        return true;
                }
                catch (IOException e)
                {
                        e.printStackTrace();
                        throw new RuntimeException(e.getMessage());
                }
        }

}
