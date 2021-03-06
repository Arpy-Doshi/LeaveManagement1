package com.brevitaz.dao.impl;

import com.brevitaz.dao.EmployeeDao;
import com.brevitaz.errors.InvalidIdException;
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
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao
{

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LeaveApplicationDaoImpl.class);


    @Value("${Employee-Index-Name}")
    private String indexName;

    private final String TYPE_NAME = "doc";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean create(Employee employee) {
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
                return true;
            }
            else
            {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //throw new RuntimeException("Not Created");
        }
        return false;
    }

    @Override
    public List<Employee> getAll()  {
        List<Employee> employees = new ArrayList<>();
        SearchRequest request = new SearchRequest(indexName);
        request.types(TYPE_NAME);

        try {
            SearchResponse response = client.search(request);

            if(response.getHits().totalHits > 0 && response.status() == RestStatus.OK)
            {
                SearchHit[] hits = response.getHits().getHits();

                Employee employee;

                for (SearchHit hit : hits)
                {
                    employee = objectMapper.readValue(hit.getSourceAsString(), Employee.class);
                    employees.add(employee);
                }

                return employees;
            }

        }
        catch (IOException e)
        {
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
        System.out.println("dao is called.");

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
    public Employee getById(String id) {
        GetRequest getRequest = new GetRequest(
                indexName,
                TYPE_NAME,
                id);

        try {
            GetResponse response = client.get(getRequest);
            if (response.isExists())
            {
                Employee employee = objectMapper.readValue(response.getSourceAsString(), Employee.class);
                LOGGER.info("Retrieving employee having id "+id);
                return employee;
            }
            else
            {
                LOGGER.error("Employee with id {} does not exist",id);
                throw new InvalidIdException("Employee with Id "+id+" doesn't exists");
            }
        }
        catch (IOException| NullPointerException e)
        {
            LOGGER.error("Error while executing getByID method of Empployee : "+e.getMessage());
        }
        return null;
        }
}
