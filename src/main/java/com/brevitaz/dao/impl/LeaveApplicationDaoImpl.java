package com.brevitaz.dao.impl;

import com.brevitaz.dao.LeaveApplicationDao;
import com.brevitaz.model.LeaveApplication;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;


@Repository
public class LeaveApplicationDaoImpl implements LeaveApplicationDao
{
    @Value("${LeaveApplication-Index-Name}")
    private String indexName;

    private final String TYPE_NAME = "doc";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public boolean request(LeaveApplication leaveApplication) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        IndexRequest request = new IndexRequest(
                indexName,
                TYPE_NAME,leaveApplication.getId()
        );
        try {
            String json = objectMapper.writeValueAsString(leaveApplication);
            request.source(json, XContentType.JSON);
            IndexResponse indexResponse  = client.index(request);
            System.out.println(indexResponse);
            if(indexResponse.status() == RestStatus.CREATED)
            {
                return true;
            }
            else
            {
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cancelRequest(String id){
        DeleteRequest request = new DeleteRequest(
                indexName,
                TYPE_NAME,
                id);

        try {
            DeleteResponse response = client.delete(request);
            System.out.println(response.status());

            System.out.println(response);
            if(response.status() == RestStatus.OK)
            {
                return true;
            }
            else
            {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateRequest(LeaveApplication leaveApplication,String id){

       try{
           objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        UpdateRequest request = new UpdateRequest(
                indexName,TYPE_NAME,
                id).doc(objectMapper.writeValueAsString(leaveApplication), XContentType.JSON);
        UpdateResponse updateResponse = client.update(request);
       if(updateResponse.status() == RestStatus.OK )
       {
           return true;
       }
       else
       {
           return false;
       }
       } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Update: "+updateResponse);
        return false;
    }

    @Override
    public LeaveApplication getById(String id) {
        GetRequest getRequest = new GetRequest(
                indexName,
                TYPE_NAME,
                id);
        try {
            GetResponse getResponse = client.get(getRequest);
            LeaveApplication leaveApplication = objectMapper.readValue(getResponse.getSourceAsString(), LeaveApplication.class);
            System.out.println(leaveApplication.getStatus());
            if(getResponse.isExists()) {
                return leaveApplication;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<LeaveApplication> getByEmployeeId(String employeeId)
    {
  /*      SearchRequest request = new SearchRequest(INDEX_NAME);
        request.types(TYPE_NAME);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.boolQuery().must(termQuery("emp_id", eid)));
        request.source(sourceBuilder);

        SearchResponse response;
        List<LeaveApplication> leaveApplications=new ArrayList<>();

        response = client.getClient().search(request);

        SearchHit[] hits = response.getHits().getHits();

        LeaveApplication leaveApplication;
        for (SearchHit hit : hits)
        {
            leaveApplication = objectMapper.readValue(hit.getSourceAsString(), LeaveApplication.class);
            leaveApplications.add(leaveApplication);
        }

        return leaveApplications;


*/
        SearchRequest request = new SearchRequest(indexName);
        request.types(TYPE_NAME);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.boolQuery().must(matchQuery("employeeId", employeeId)));
        request.source(sourceBuilder);

        SearchResponse response;
        List<LeaveApplication> leaveApplications=new ArrayList<>();

        try {
            response = client.search(request);
            SearchHit[] hits = response.getHits().getHits();

            LeaveApplication leaveApplication;
            for (SearchHit hit : hits)
            {
                leaveApplication = objectMapper.readValue(hit.getSourceAsString(), LeaveApplication.class);
                leaveApplications.add(leaveApplication);
            }
            if(response.status() == RestStatus.OK) {
                return leaveApplications;
            }
            else
            {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<LeaveApplication> checkRequest()  {
        SearchRequest request = new SearchRequest(indexName);
        request.types(TYPE_NAME);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.boolQuery().must(matchQuery("status", "APPLIED")));

        request.source(sourceBuilder);

        SearchResponse response;
        List<LeaveApplication> leaveApplications=new ArrayList<>();

        try {
            response = client.search(request);
            SearchHit[] hits = response.getHits().getHits();

            LeaveApplication leaveApplication;
            for (SearchHit hit : hits)
            {
                leaveApplication = objectMapper.readValue(hit.getSourceAsString(), LeaveApplication.class);
                leaveApplications.add(leaveApplication);
            }
            if(response.status() == RestStatus.OK) {
                return leaveApplications;
            }
            else
            {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean approveRequest(String id) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            UpdateRequest request = new UpdateRequest(
                    indexName,TYPE_NAME,
                    id).doc(jsonBuilder()
                            .startObject()
                            .field("status", "APPROVED")
                            .endObject());
            UpdateResponse updateResponse = client.update(request);
            System.out.println("Update: "+updateResponse);
            if (updateResponse.status() == RestStatus.OK) {
                return true;
            }
            else
            {
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean declineRequest(String id) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            UpdateRequest request = new UpdateRequest(
                    indexName,TYPE_NAME,
                    id)/*.doc(config.getObjectMapper().writeValueAsString("status","APPROVED"), XContentType.JSON)*/;

            request.doc(jsonBuilder()
                    .startObject()
                    .field("status", "REJECTED")
                    .endObject());
            UpdateResponse updateResponse = client.update(request);
            System.out.println("Update: "+updateResponse);
            if (updateResponse.status() == RestStatus.OK) {
                return true;
            }
            else
            {
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public List<LeaveApplication> getAll()  {
        List<LeaveApplication> leaveApplications = new ArrayList<>();

        try {
        SearchRequest request = new SearchRequest(indexName);
        request.types(TYPE_NAME);
        SearchResponse response = client.search(request);
        SearchHit[] hits = response.getHits().getHits();

        LeaveApplication leaveApplication;

        for (SearchHit hit : hits)
        {
            leaveApplication = objectMapper.readValue(hit.getSourceAsString(), LeaveApplication.class);
            leaveApplications.add(leaveApplication);
        }
        if(response.status() == RestStatus.OK) {
            return leaveApplications;
        }
        else
        {
            return null;
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
