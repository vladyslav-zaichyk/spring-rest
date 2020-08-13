package com.example.springtest.controller;

import com.example.springtest.controller.assembler.StudentGroupModelAssembler;
import com.example.springtest.exception.EntityNotFoundException;
import com.example.springtest.model.StudentGroup;
import com.example.springtest.repository.StudentGroupRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/groups")
@RestController
public class StudentGroupController {
    private final StudentGroupRepository repository;
    private final StudentGroupModelAssembler assembler;

    public StudentGroupController(StudentGroupRepository repository, StudentGroupModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<StudentGroup>> all() {
        List<EntityModel<StudentGroup>> groups = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(groups,
                linkTo(methodOn(StudentGroupController.class).all()).withSelfRel());
    }

    @PostMapping("/")
    StudentGroup newStudentGroup(@RequestBody StudentGroup newStudentGroup) {
        return repository.save(newStudentGroup);
    }

    @GetMapping("/{id}")
    public EntityModel<StudentGroup> one(@PathVariable Integer id) {
        StudentGroup group = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return assembler.toModel(group);
    }

    @PutMapping("/{id}")
    StudentGroup replace(@RequestBody StudentGroup newStudentGroup, @PathVariable Integer id) {
        return repository.findById(id)
                .map(studentGroup -> {
                    studentGroup.setDepartment(newStudentGroup.getDepartment());
                    studentGroup.setName(newStudentGroup.getName());
                    studentGroup.setSemester(newStudentGroup.getSemester());
                    studentGroup.setStudyPlan(newStudentGroup.getStudyPlan());
                    return repository.save(studentGroup);
                })
                .orElseGet(() -> {
                    newStudentGroup.setId(id);
                    return repository.save(newStudentGroup);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
