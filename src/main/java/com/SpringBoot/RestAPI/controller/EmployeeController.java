package com.SpringBoot.RestAPI.controller;

import com.SpringBoot.RestAPI.dto.EmployeeDTO;
import com.SpringBoot.RestAPI.entities.EmployeeEntity;
import com.SpringBoot.RestAPI.repositories.EmployeeRepository;
import com.SpringBoot.RestAPI.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId){
        Optional<EmployeeDTO> employeeDTO=  employeeService.getEmployeeById(employeeId);
       return employeeDTO
               .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
               .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false,name ="inputAge") Integer age,
                                                @RequestParam(required = false) String name){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){
        EmployeeDTO employeeDTO= employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO,@PathVariable Long employeeId ){

        return ResponseEntity.ok(employeeService.updateEmployee(employeeDTO,employeeId));
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long employeeId){
        boolean gotDeleted= employeeService.deleteEmployee(employeeId);
        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();

    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeByid(@RequestBody Map<String,Object> updates,
                                             @PathVariable Long employeeId){

        EmployeeDTO employeeDTO= employeeService.updatePartialEmployeeById(updates,employeeId);
        if (employeeDTO==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }






}
