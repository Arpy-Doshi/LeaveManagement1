package com.brevitaz.service.impl;

import com.brevitaz.dao.EmployeeDao;
import com.brevitaz.errors.EmployeeNotFoundException;
import com.brevitaz.errors.IndexNotFoundException;
import com.brevitaz.errors.InvalidIdException;
import com.brevitaz.model.Employee;
import com.brevitaz.service.EmployeeService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
      /*  if(employee.getId().trim().length() <= 0|| employee.getName().trim().length()<=0) {
            throw new RuntimeException("Field is null");
        }
      */  /*else {

        }*/
        return employeeDao.create(employee);
    }

    @Override
    public boolean delete(String id)
    {/*
        if (id.trim().length() <= 0)
            throw new InvalidIdException("Id is null!!!");
*/
        /*Employee employee =*/ employeeDao.getById(id);

     /*   if (employee != null)
     */       return employeeDao.delete(id);
       /* else
            throw new EmployeeNotFoundException("Employee with Id "+id+" doesn't exists!!!");*/
    }

    @Override
    public boolean update(Employee employee, String id)//TODO:service layer should handle exception
    {//TODO:naming convention i.e. Employee employeeToBeUpdated in update
        /*StringEntity entity = null;
        Response isIndexExists = client.exists(new GetRequest(indexName), )

        GetIndexRequest existsRequest = new GetIndexRequest();
        GetIndexRequest res = existsRequest.indices(indexName);
*/
        /*if (id.trim().length() <= 0)
            throw  new InvalidIdException("Id is null!!!!");
*/
  /*      if(employee.getId().trim().length() <= 0)
            throw  new InvalidIdException("Id is null!!!");
*/
        if (employee.getId().equals(id))
        {/*
            if(employee!=null)*/
                return employeeDao.update(employee,id);
           /* else
                throw  new EmployeeNotFoundException("Employee with Id "+id+" doesn't exists!!!");
*/      }
        else
            throw new InvalidIdException("Id doesn't match!!!");

    }

    @Override
    public Employee getById(String id)
    {
  /*      if (id.trim().length() <= 0)
            throw  new InvalidIdException("Id is null!!!!");
*/
        /*Employee employee =*/ employeeDao.getById(id);

       /* if (employee != null)
       */     return employeeDao.getById(id);
       /* else
            throw new EmployeeNotFoundException("Employee with Id "+id+" doesn't exists!!!");*/
    }

    @Override
    public List<Employee> getAll()
    {
/*
        boolean exists = client.admin().indices()
                .prepareExists(indexName)
                .execute().actionGet().isExists();

        if (exists == false)
            throw new IndexNotFoundException("Index doesn't exists!!!!!");
        else
*/
            return employeeDao.getAll();
    }
}

