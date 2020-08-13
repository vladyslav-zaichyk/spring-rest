package com.example.springtest.controller;

import com.example.springtest.controller.assembler.StudentModelAssembler;
import com.example.springtest.exception.EntityNotFoundException;
import com.example.springtest.model.Student;
import com.example.springtest.repository.StudentRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("students")
@RestController
public class StudentController {
    private final StudentRepository repository;
    private final StudentModelAssembler assembler;

    public StudentController(StudentRepository repository, StudentModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Student>> all() {
        List<EntityModel<Student>> students = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(students,
                linkTo(methodOn(StudentController.class).all()).withSelfRel());
    }

    @PostMapping("/")
    Student newStudent(@RequestBody Student newStudent) {
        return repository.save(newStudent);
    }

    @GetMapping("/{id}")
    public EntityModel<Student> one(@PathVariable Integer id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return assembler.toModel(student);
    }

    @PutMapping("/{id}")
    Student replace(@RequestBody Student newStudent, @PathVariable Integer id) {
        return repository.findById(id)
                .map(student -> {
                    student.setGroup(newStudent.getGroup());
                    student.setPerson(newStudent.getPerson());
                    return repository.save(student);
                })
                .orElseGet(() -> {
                    newStudent.setId(id);
                    return repository.save(newStudent);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
