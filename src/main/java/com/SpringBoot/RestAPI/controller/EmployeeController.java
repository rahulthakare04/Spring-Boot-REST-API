package com.SpringBoot.RestAPI.controller;

import com.SpringBoot.RestAPI.dto.EmployeeDTO;
import com.SpringBoot.RestAPI.entities.EmployeeEntity;
import com.SpringBoot.RestAPI.repositories.EmployeeRepository;
import com.SpringBoot.RestAPI.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private  final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/getSecretMessage")
    public String getSecretMessage(){
        return "Secret Message is : I AM BATMAN";
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId){
        return  employeeService.getEmployeeById(employeeId);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false,name ="inputAge") Integer age,
                                                @RequestParam(required = false) String name){
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
        return employeeService.createNewEmployee(inputEmployee);
    }

    @PutMapping
    public String updateEmployee(){
        return "Hello form Put";
    }




}
