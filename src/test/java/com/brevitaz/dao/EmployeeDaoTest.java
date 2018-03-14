package com.brevitaz.dao;

import com.brevitaz.model.Employee;
import com.carrotsearch.randomizedtesting.RandomizedRunner;
import com.carrotsearch.randomizedtesting.annotations.ThreadLeakScope;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.util.List;

import static junit.framework.TestCase.assertFalse;

@RunWith(RandomizedRunner.class)
@ThreadLeakScope(ThreadLeakScope.Scope.NONE)
@SpringBootTest
public class EmployeeDaoTest {

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();


    @Autowired
    private EmployeeDao employeeDao;

    @Test(expected = RuntimeException.class)
    public void createTest() {
        Employee employee = new Employee();
        employee.setId("11");
        employee.setName("Yash");
        employee.setDepartment("Java");
        employeeDao.create(employee);

        Employee employee1 = employeeDao.getById("11");
        Employee employee2 = employeeDao.getById("12");
        Assert.assertEquals(employee2.getName(), employee.getName());
//        Assert.assertNotEquals("Arpy",employee.getName());
//        employeeDao.delete("11");
    }

    @Test/*(expected = NullPointerException.class)*/
    public void getAllTest() {
        Employee employee = new Employee();
        employee.setId("11");
        employee.setName("Yash");
        employee.setDepartment("Java");
        employeeDao.create(employee);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Employee> employees = employeeDao.getAll();
        int size = employees.size();

        Assert.assertEquals(1,size);
        Assert.assertNotEquals(2,size);
        employeeDao.delete("11");

    }

    @Test(expected = NullPointerException.class)
    public void getByIdTest() {
        Employee employee = new Employee();
        employee.setId(null);
        employee.setName("Yash");
        employee.setDepartment("Java");
        employeeDao.create(employee);

        Employee employee1 = employeeDao.getById(null);
        Assert.assertEquals(employee1.getName(),employee.getName());
        employeeDao.delete(null);
    }

    @Test/*(expected = NullPointerException.class)*/
    public void update() {
        Employee employee = new Employee();
        employee.setId("11");
        employee.setName("Yash");
        employee.setDepartment("Java");
        employeeDao.create(employee);

        Employee employee1 = new Employee();
        employee1.setName("Arpy");
        employeeDao.update(employee1,"11");

        Employee employee3 = employeeDao.getById("11");
        Assert.assertEquals(employee3.getName(),employee1.getName());
        Assert.assertEquals(employee3.getId(),employee1.getId());
        employeeDao.delete("11");
    }

    @Test/*(expected = NullPointerException.class)*/
    public void delete() {
        Employee employee = new Employee();
        employee.setId("11");
        employee.setName("Yash");
        employee.setDepartment("Java");
        employeeDao.create(employee);

        employeeDao.delete("11");
        Employee employee1 = employeeDao.getById("11");

        Assert.assertNull(employee1);
    }


    @Test(expected = IllegalArgumentException.class)
    public void test1() throws Throwable{
        assertFalse(throwException());
    }

    private boolean throwException(){
        throw new IllegalArgumentException();
    }
}
