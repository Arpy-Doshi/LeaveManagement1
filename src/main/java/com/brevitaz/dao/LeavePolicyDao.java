package com.brevitaz.dao;

import com.brevitaz.model.LeavePolicy;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LeavePolicyDao
{
    public ResponseEntity<String> create(LeavePolicy leavePolicy);
    public ResponseEntity<String> update(LeavePolicy leavePolicy,String id);
    public ResponseEntity<String> delete( String id);
    public LeavePolicy getById(String id);
    public List<LeavePolicy> getAll();

}
