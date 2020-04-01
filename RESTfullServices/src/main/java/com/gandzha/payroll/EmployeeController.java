package com.gandzha.payroll;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeRepository repository;

    //Aggregate root

    @GetMapping("/employees")
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @PostMapping("/employees")
    public Employee create(@RequestBody Employee employee) {
        return repository.save(employee);
    }

    //Single item

    @GetMapping("/employees/{id}")
    public Employee get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    public Employee update(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() ->{
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
