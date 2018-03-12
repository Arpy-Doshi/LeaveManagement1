package com.brevitaz.dao.impl;

import com.brevitaz.config.Config;
import com.brevitaz.dao.EmployeeDao;
import com.brevitaz.model.Employee;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao
{

    @Value("${Employee-Index-Name}")
    private String indexName;

    private final String TYPE_NAME = "doc";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResponseEntity<String> create(Employee employee) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        IndexRequest request = new IndexRequest(
                indexName,
                TYPE_NAME,employee.getId()
        );

        try {
            String json = objectMapper.writeValueAsString(employee);
            request.source(json, XContentType.JSON);
            IndexResponse indexResponse = client.index(request);
            System.out.println(indexResponse);
            if(indexResponse.status() == RestStatus.CREATED)
            {
                return new ResponseEntity<>("Created", HttpStatus.CREATED);
            }
            else
            {
                return new ResponseEntity<>("Not Created", HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Not Created", HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<Employee> getAll()  {
        List<Employee> employees = new ArrayList<>();
        SearchRequest request = new SearchRequest(indexName);
        request.types(TYPE_NAME);

        try {
            SearchResponse response = client.search(request);
            SearchHit[] hits = response.getHits().getHits();

            Employee employee;

            for (SearchHit hit : hits)
            {
                employee = objectMapper.readValue(hit.getSourceAsString(), Employee.class);
                employees.add(employee);
            }

            if(response.status() == RestStatus.OK)
            {
                return employees;
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
    public boolean update(Employee employee,String id){
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            UpdateRequest updateRequest = new UpdateRequest(
                    indexName,TYPE_NAME,
                    id).doc(objectMapper.writeValueAsString(employee), XContentType.JSON);
            UpdateResponse updateResponse = client.update(updateRequest);
            System.out.println("Update: "+updateResponse);
            if(updateResponse.status() == RestStatus.OK)
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
    public boolean delete(String id) {
        DeleteRequest request = new DeleteRequest(
                indexName,
                TYPE_NAME,
                id);

        try {
            DeleteResponse response = client.delete(request);
            if(response.status() == RestStatus.OK)
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
    public Employee getById(String id)  {
        GetRequest getRequest = new GetRequest(
                indexName,
                TYPE_NAME,
                id);


        try {

            GetResponse response = client.get(getRequest);
            Employee employee = objectMapper.readValue(response.getSourceAsString(), Employee.class);
            if (response.isExists()) {
                return employee;
            } else {
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
        }
}
