package com.my.service;

import org.springframework.stereotype.Service;
import com.my.dto.Employee;

@Service
public class EmployeeService {
  public Employee createEmployee(String empId, String fname, String sname) {
    Employee emp = new Employee();
    emp.setEmpId(empId);
    emp.setFirstName(fname);
    emp.setSecondName(sname);
    return emp;
  }

  public void deleteEmployee(String empId) {}
}
