package com.brevitaz.dao.impl;

import com.brevitaz.config.Config;
import com.brevitaz.dao.LeavePolicyRuleDao;
import com.brevitaz.model.LeavePolicyRule;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class LeavePolicyRuleDaoImpl implements LeavePolicyRuleDao
{
    @Value("${LeavePolicyRule-Index-Name}")
    private String indexName;

    private final String TYPE_NAME = "doc";

    @Autowired
    private Config config;


    @Override
    public boolean create(LeavePolicyRule leavePolicyRule)  {
        config.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        IndexRequest request = new IndexRequest(
                indexName,
                TYPE_NAME,leavePolicyRule.getId()
        );

        try {
            String json = config.getObjectMapper().writeValueAsString(leavePolicyRule);

            request.source(json, XContentType.JSON);

            IndexResponse indexResponse = config.getClient().index(request);
            System.out.println(indexResponse);

            if (indexResponse.status() == RestStatus.CREATED) {
                return true;
            } else {
                return false;
            }
        }
            catch (IOException e) {
                e.printStackTrace();
            }

        return false;
    }


    @Override
    public boolean update(LeavePolicyRule leavePolicyRule,String id) {
        config.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        UpdateRequest updateRequest = null;
        try {
            updateRequest = new UpdateRequest(
                    indexName, TYPE_NAME,
                    id).doc(config.getObjectMapper().writeValueAsString(leavePolicyRule), XContentType.JSON);
            UpdateResponse updateResponse = config.getClient().update(updateRequest);

            System.out.println("Update: " + updateResponse);
            if (updateResponse.status() == RestStatus.OK) {
                return true;
            } else {
                return false;
            }
        }
            catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id)  {
        DeleteRequest request = new DeleteRequest(
                indexName,
                TYPE_NAME,
                id);

        try {
            DeleteResponse response = config.getClient().delete(request);

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
         }
         catch (IOException e) {
            e.printStackTrace();
         }

        return true;
    }

    @Override
    public LeavePolicyRule getById(String id) {
        GetRequest getRequest = new GetRequest(
                indexName,
                TYPE_NAME,
                id);
        try {
            GetResponse response = config.getClient().get(getRequest);
            LeavePolicyRule leavePolicyRule = config.getObjectMapper().readValue(response.getSourceAsString(),LeavePolicyRule.class);

            if(response.isExists())
            {
                return leavePolicyRule;
            }
            else
            {
                return null;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<LeavePolicyRule> getAll(){
        List<LeavePolicyRule> leavePolicyRules = new ArrayList<>();
        SearchRequest request = new SearchRequest(indexName);
        request.types(TYPE_NAME);

        try {
            SearchResponse response = config.getClient().search(request);
            SearchHit[] hits = response.getHits().getHits();

            LeavePolicyRule leavePolicyRule;

            for (SearchHit hit : hits)
            {
                leavePolicyRule = config.getObjectMapper().readValue(hit.getSourceAsString(), LeavePolicyRule.class);
                leavePolicyRules.add(leavePolicyRule);
            }

            if(response.status() == RestStatus.OK)
            {
                return leavePolicyRules;
            }
            else
            {
                return null;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}
