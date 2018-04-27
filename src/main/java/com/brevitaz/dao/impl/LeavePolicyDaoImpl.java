package com.brevitaz.dao.impl;

import com.brevitaz.dao.LeavePolicyDao;
import com.brevitaz.errors.InvalidIdException;
import com.brevitaz.model.LeavePolicy;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.nio.reactor.ssl.SSLBuffer;
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
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LeavePolicyDaoImpl implements LeavePolicyDao
{
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LeavePolicyDaoImpl.class);

    @Value("${LeavePolicy-Index-Name}")
    private String indexName;

    private final String TYPE_NAME = "doc";

    /*@Autowired
    private Config config;
*/
    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;



    @Override
    public boolean create(LeavePolicy leavePolicy)  {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try
        {
            IndexRequest request = new IndexRequest(
                    indexName,
                    TYPE_NAME,leavePolicy.getId());

            String json = objectMapper.writeValueAsString(leavePolicy);

            request.source(json, XContentType.JSON);

            IndexResponse indexResponse = client.index(request);
            System.out.println(indexResponse);
            if (indexResponse.status() == RestStatus.CREATED)
            {
                LOGGER.info("Leave Policy is created with id "+leavePolicy.getId());
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (IOException e)
        {
          LOGGER.error("Error while creating Leave Policy : "+e.getMessage());
        }
        return false;
    }


    @Override
    public boolean update(LeavePolicy leavePolicy,String id) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try
        {
            UpdateRequest updateRequest = new UpdateRequest(
                    indexName,TYPE_NAME,
                    id).doc(objectMapper.writeValueAsString(leavePolicy), XContentType.JSON);


            UpdateResponse updateResponse = client.update(updateRequest);

            if(updateResponse.status() == RestStatus.OK)
            {
                LOGGER.info("Leave Policy is updaetd having id "+id);
                return true;
            }
            else
            {
                return false;
            }
        }

        catch (IOException e)
        {
            LOGGER.error("Error while updating Leave Policy : "+e.getMessage());
        }
        return false;
    }

    @Override
    public LeavePolicy getLatestPolicy() {

        try {
            List<LeavePolicy> leavePolicies = new ArrayList<>();

            SearchRequest request = new SearchRequest(indexName);
            request.types(TYPE_NAME);

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.sort("createdDate",SortOrder.DESC);

            request.source(sourceBuilder);

            SearchResponse response = client.search(request);
            if(response.status() == RestStatus.OK)
            {

                SearchHit[] hits = response.getHits().getHits();
                LeavePolicy leavePolicy;

                for (SearchHit hit : hits)
                {
                    leavePolicy = objectMapper.readValue(hit.getSourceAsString(), LeavePolicy.class);
                    leavePolicies.add(leavePolicy);
                }
                LOGGER.info("Retrieving Latest Policy");
                return leavePolicies.get(0);
            }
            else
            {
                return null;
            }
        }
        catch (IOException e)
        {
            LOGGER.error("Error while getting latest policy :"+e.getMessage());
            //  throw new IndexNotFoundException("Index doesn't exists!!!");
        }
        return null;

    }

    /* @Override
     public boolean delete(String id)  {
         try
         {
             DeleteRequest request = new DeleteRequest(
                     indexName,
                     TYPE_NAME,id);

             DeleteResponse response = client.delete(request);

             System.out.println(response);

             if(response.status() == RestStatus.OK)
             {
                 return true;
             }
             else
             {
                 return false;
             }
         }
         catch (IOException e)
         {
             e.printStackTrace();
             //throw new InvalidIdException("LeavePolicy doesn't exists!!!");
         }
         return false;
     }
 */
    @Override
    public LeavePolicy getById(String id) {
        try
        {
            GetRequest getRequest = new GetRequest(
                    indexName,
                    TYPE_NAME,id);

            GetResponse getResponse= client.get(getRequest);
            if(getResponse.isExists())
            {
                LeavePolicy leavePolicy = objectMapper.readValue(getResponse.getSourceAsString(),LeavePolicy.class);
                LOGGER.info("Retrieving Leave Policy having id "+id);
                return leavePolicy;
            }
        }
        catch (IOException e)
        {
            LOGGER.error("Error while executing getById method of Leave Policy : "+e.getMessage());
            //throw new InvalidIdException("LeavePolicy doesn't exists!!!");

        }
        return null;
    }

    @Override
    public List<LeavePolicy> getAll() {
        try {
        List<LeavePolicy> leavePolicies = new ArrayList<>();

        SearchRequest request = new SearchRequest(indexName);
        request.types(TYPE_NAME);

        SearchResponse response = client.search(request);
        if(response.status() == RestStatus.OK)
        {

            SearchHit[] hits = response.getHits().getHits();
            LeavePolicy leavePolicy;

            for (SearchHit hit : hits)
            {
                leavePolicy = objectMapper.readValue(hit.getSourceAsString(), LeavePolicy.class);
                leavePolicies.add(leavePolicy);
            }
            LOGGER.info("Retrieving all Leave Policies");
            return leavePolicies;
        }
        else
        {
            return null;
        }
        }
        catch (IOException e)
        {
          LOGGER.error("Error while retrieving all Leave Applications : "+e.getMessage());
          //  throw new IndexNotFoundException("Index doesn't exists!!!");
        }
        return null;
    }

}
