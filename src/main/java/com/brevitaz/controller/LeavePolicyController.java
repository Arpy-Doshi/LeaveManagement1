/*
package com.brevitaz.controller;

import com.brevitaz.model.LeavePolicy;
import com.brevitaz.service.LeavePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leave-policies")
public class LeavePolicyController {

    @Autowired
    private LeavePolicyService leavePolicyService;

    @RequestMapping(value = "" , method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody LeavePolicy leavePolicy){
        return leavePolicyService.create(leavePolicy);
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.PUT)
    public ResponseEntity<String> update(@RequestBody LeavePolicy leavePolicy,@PathVariable String id){
        return leavePolicyService.update(leavePolicy,id);
    }
    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable String id) {
        return leavePolicyService.delete(id);
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.GET)
    public LeavePolicy getById(@PathVariable String id) {
        return leavePolicyService.getById(id);
    }

    @RequestMapping(value = "" , method = RequestMethod.GET)
    public List<LeavePolicy> getAll()  {
        return leavePolicyService.getAll();
    }





}
*/
