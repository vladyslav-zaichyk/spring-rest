package com.example.springtest.controller;

import com.example.springtest.controller.assembler.DepartmentModelAssembler;
import com.example.springtest.exception.EntityNotFoundException;
import com.example.springtest.model.Department;
import com.example.springtest.repository.DepartmentRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/departments")
@RestController
public class DepartmentController {
    private final DepartmentRepository repository;
    private final DepartmentModelAssembler assembler;

    public DepartmentController(DepartmentRepository repository, DepartmentModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Department>> all() {
        List<EntityModel<Department>> departments = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(departments,
                linkTo(methodOn(DepartmentController.class).all()).withSelfRel());
    }

    @PostMapping("/")
    Department newDepartment(@RequestBody Department newDepartment) {
        return repository.save(newDepartment);
    }

    @GetMapping("/{id}")
    public EntityModel<Department> one(@PathVariable Integer id) {
        Department department = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return assembler.toModel(department);
    }

    @PutMapping("/{id}")
    Department replace(@RequestBody Department newDepartment, @PathVariable Integer id) {
        return repository.findById(id)
                .map(department -> {
                    department.setName(newDepartment.getName());
                    department.setFaculty(newDepartment.getFaculty());
                    department.setHeadInstructor(newDepartment.getHeadInstructor());
                    return repository.save(department);
                })
                .orElseGet(() -> {
                    newDepartment.setId(id);
                    return repository.save(newDepartment);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
