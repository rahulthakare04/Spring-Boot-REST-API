package com.SpringBoot.RestAPI.services;

import com.SpringBoot.RestAPI.dto.EmployeeDTO;
import com.SpringBoot.RestAPI.entities.EmployeeEntity;
import com.SpringBoot.RestAPI.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }



    public Optional<EmployeeDTO> getEmployeeById(Long employeeId) {
//        Optional<EmployeeEntity> employeeEntity= employeeRepository.findById(employeeId);
//         return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1,EmployeeDTO.class));

        return employeeRepository.findById(employeeId).map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class));

    }

    public List<EmployeeDTO> getAllEmployees() {
       List<EmployeeEntity> employeeEntities=employeeRepository.findAll();
       return employeeEntities
               .stream()
               .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class))
               .toList();
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity toSaveEntity=modelMapper.map(inputEmployee,EmployeeEntity.class);
        EmployeeEntity employeeEntity= employeeRepository.save(toSaveEntity);
        return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, Long employeeId) {
        EmployeeEntity employeeEntity=modelMapper.map(employeeDTO,EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity newEmployeeEntity=employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity,EmployeeDTO.class);

    }

    public boolean isExistsByEmoloyeeId(Long employeeId){
        return employeeRepository.existsById(employeeId);
    }
    public boolean deleteEmployee(Long employeeId) {
        boolean exist=isExistsByEmoloyeeId(employeeId);
        if(!exist) return false;
        employeeRepository.deleteById(employeeId);
        return true;

    }

    public EmployeeDTO updatePartialEmployeeById(Map<String, Object> updates, Long employeeId) {
        boolean exist=isExistsByEmoloyeeId(employeeId);
        if(!exist) return null;
        EmployeeEntity employeeEntity=employeeRepository.findById(employeeId).get();
        updates.forEach((field,value)->{
            Field fieldToBeUpdated= ReflectionUtils.findRequiredField(EmployeeEntity.class,field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        });

       return modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);
    }
}
