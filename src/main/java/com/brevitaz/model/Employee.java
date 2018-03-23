package com.brevitaz.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@EntityScan
public class Employee {

    @NotNull/*(message = "Id shouldn't be null")*/
   /* @Size(min = 1,max = 20)
   */ private String id;

    @NotNull
    @Size(min = 2,max = 20 , message = "name should have minimun 2 and maximum 20 characters")
    private String name;

    @NotNull
    private Date dateOfJoining;

    private String department;

    public String getId() {
        return id;
    }

    public void setId(String id)
    {
        Pattern p1 = Pattern.compile("^[ A-Za-z0-9]+$");
        Matcher m = p1.matcher(id);
        boolean b = m.matches();
        if (b == true)

            this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
/*
        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        boolean isMatched = matcher.matches();
*/
        Pattern p = Pattern.compile("^[ A-Za-z]+$");
        Matcher m = p.matcher(name);
        boolean b = m.matches();
        if (b == true)
            this.name = name;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateOfJoining=" + dateOfJoining +
                ", department='" + department + '\'' +
                '}';
    }
}
