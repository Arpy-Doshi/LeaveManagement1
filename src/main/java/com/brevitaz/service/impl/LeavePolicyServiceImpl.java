package com.brevitaz.service.impl;

import com.brevitaz.dao.LeavePolicyDao;
import com.brevitaz.model.LeavePolicy;
import com.brevitaz.service.LeavePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeavePolicyServiceImpl implements LeavePolicyService
{

    @Autowired
    private LeavePolicyDao leavePolicyDao;

    @Override
    public ResponseEntity<String> create(LeavePolicy leavePolicy) {
        if(leavePolicy.getId().trim().length() <= 0|| leavePolicy.getName().trim().length()<=0)
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);

            //Employee employee = employeeService.getByUsernameAndPassword(username,password);

        else
            return leavePolicyDao.create(leavePolicy);
    }

    @Override
    public ResponseEntity<String> update(LeavePolicy leavePolicy, String id)
    {
       /*StringEntity entity = null;
        Response isIndexExists = client.exists(new GetRequest(indexName), )

        GetIndexRequest existsRequest = new GetIndexRequest();
        GetIndexRequest res = existsRequest.indices(indexName);
*/
        if (id.trim().length() <= 0)
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);

        if(leavePolicy.getId().trim().length() <= 0)
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);

        if(leavePolicy!=null && leavePolicy.getId().equals(id))
            return leavePolicyDao.update(leavePolicy,id);
        else
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<String> delete(String id)
    {
        if (id.trim().length() <= 0)
            return new ResponseEntity<>("Not Allowed", HttpStatus.FORBIDDEN);

        else
            return leavePolicyDao.delete(id);
    }

    @Override
    public LeavePolicy getById(String id) {
        return null;
    }

    @Override
    public List<LeavePolicy> getAll() {
        return null;
    }
}
