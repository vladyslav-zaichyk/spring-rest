package com.example.springtest.controller;

import com.example.springtest.controller.assembler.InstructorModelAssembler;
import com.example.springtest.exception.EntityNotFoundException;
import com.example.springtest.model.Instructor;
import com.example.springtest.repository.InstructorRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/instructors")
@RestController
public class InstructorController {
    private final InstructorRepository repository;
    private final InstructorModelAssembler assembler;

    public InstructorController(InstructorRepository repository, InstructorModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Instructor>> all() {
        List<EntityModel<Instructor>> instructors = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(instructors,
                linkTo(methodOn(InstructorController.class).all()).withSelfRel());
    }

    @PostMapping("/")
    Instructor newInstructor(@RequestBody Instructor newInstructor) {
        return repository.save(newInstructor);
    }

    @GetMapping("/{id}")
    public EntityModel<Instructor> one(@PathVariable Integer id) {
        Instructor instructor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return assembler.toModel(instructor);
    }

    @PutMapping("/{id}")
    Instructor replace(@RequestBody Instructor newInstructor, @PathVariable Integer id) {
        return repository.findById(id)
                .map(instructor -> {
                    instructor.setPerson(newInstructor.getPerson());
                    instructor.setAcademicRank(newInstructor.getAcademicRank());
                    return repository.save(instructor);
                })
                .orElseGet(() -> {
                    newInstructor.setId(id);
                    return repository.save(newInstructor);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
