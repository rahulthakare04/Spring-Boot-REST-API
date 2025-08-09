package com.SpringBoot.RestAPI.controller;

import com.SpringBoot.RestAPI.dto.EmployeeDTO;
import com.SpringBoot.RestAPI.entities.EmployeeEntity;
import com.SpringBoot.RestAPI.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(path = "/getSecretMessage")
    public String getSecretMessage(){
        return "Secret Message is : I AM BATMAN";
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeEntity getEmployeeById(@PathVariable Long employeeId){
        return  employeeRepository.findById(employeeId).orElse(null);
    }

    @GetMapping
    public List<EmployeeEntity> getAllEmployees(@RequestParam(required = false,name ="inputAge") Integer age,
                                                @RequestParam(required = false) String name){
        return employeeRepository.findAll();
    }

    @PostMapping
    public EmployeeEntity createNewEmployee(@RequestBody EmployeeEntity inputEmployee){
        return employeeRepository.save(inputEmployee);
    }

    @PutMapping
    public String updateEmployee(){
        return "Hello form Put";
    }




}
