package com.SpringBoot.RestAPI.controller;

import com.SpringBoot.RestAPI.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    @GetMapping(path = "/getSecretMessage")
    public String getSecretMessage(){
        return "Secret Message is : I AM BATMAN";
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId){
        return  new EmployeeDTO(employeeId,"rahul","rahul@gmail.com",21, LocalDate.of(2025,12,24),true);
    }

    @GetMapping
    public String getAllEmployees(@RequestParam(required = false,name ="inputAge") Integer age,
                                  @RequestParam(required = false) String name){
        return "age is "+age+" name is "+name;
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
        inputEmployee.setId(100L);
        return inputEmployee;
    }

    @PutMapping
    public String updateEmployee(){
        return "Hello form Put";
    }




}
