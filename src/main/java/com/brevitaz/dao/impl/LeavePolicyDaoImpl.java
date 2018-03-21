package com.brevitaz.dao.impl;

import com.brevitaz.dao.LeavePolicyDao;
import com.brevitaz.errors.IndexNotFoundException;
import com.brevitaz.errors.InvalidIdException;
import com.brevitaz.model.LeavePolicy;
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
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LeavePolicyDaoImpl implements LeavePolicyDao
{
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
           // throw new InvalidIdException("not created!!!");
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
            System.out.println(updateResponse);

            if(updateResponse.status() == RestStatus.OK)
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

    @Override
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
                return objectMapper.readValue(getResponse.getSourceAsString(),LeavePolicy.class);

            }
            else
            {
                throw new InvalidIdException("LeavePolicy doesn't exists!!!");            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
            //throw new InvalidIdException("LeavePolicy doesn't exists!!!");

        }
        return null;
    }

    @Override
    public List<LeavePolicy> getAll()  {
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
            return leavePolicies;
        }
        else
        {
            return null;
        }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IndexNotFoundException("Index doesn't exists!!!");
        }
    }


}
