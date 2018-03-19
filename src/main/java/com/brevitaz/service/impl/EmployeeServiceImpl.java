package com.brevitaz.service.impl;

import com.brevitaz.dao.EmployeeDao;
import com.brevitaz.model.Employee;
import com.brevitaz.service.EmployeeService;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService
{
    @Value("${Employee-Index-Name}")
    private String indexName;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private EmployeeDao employeeDao;


    @Override
    public boolean create(Employee employee) {
        if(employee.getId().trim().length() <= 0|| employee.getName().trim().length()<=0) {
            throw new RuntimeException("Field is null");
        }
        //Employee employee = employeeService.getByUsernameAndPassword(username,password);

        else {
            return employeeDao.create(employee);
        }
    }

    @Override
    public boolean delete(String id)
    {
        System.out.println("service is called.");

        if (id.trim().length() <= 0)
            throw new RuntimeException("Bad request!!!");

        else
            return employeeDao.delete(id);
    }

    @Override
    public boolean update(Employee employee, String id)//TODO:service layer should handle exception
    {//TODO:naming convention i.e. Employee employeeToBeUpdated in update
        /*StringEntity entity = null;
        Response isIndexExists = client.exists(new GetRequest(indexName), )

        GetIndexRequest existsRequest = new GetIndexRequest();
        GetIndexRequest res = existsRequest.indices(indexName);
*/
        if (id.trim().length() <= 0)
            throw  new RuntimeException("Bad request!!!!");

        if(employee.getId().trim().length() <= 0)
            throw  new RuntimeException("Bad request");

        if(employee!=null && employee.getId().equals(id))
            return employeeDao.update(employee,id);
        else
            throw  new RuntimeException("Bad Request");

    }

    @Override
    public Employee getById(String id) {
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.getAll();
    }
}
