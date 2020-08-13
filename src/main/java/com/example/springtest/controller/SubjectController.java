package com.example.springtest.controller;

import com.example.springtest.controller.assembler.SubjectModelAssembler;
import com.example.springtest.exception.EntityNotFoundException;
import com.example.springtest.model.Subject;
import com.example.springtest.repository.SubjectRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/subjects")
@RestController
public class SubjectController {
    private final SubjectRepository repository;
    private final SubjectModelAssembler assembler;

    public SubjectController(SubjectRepository repository, SubjectModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Subject>> all() {
        List<EntityModel<Subject>> subjects = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(subjects,
                linkTo(methodOn(SubjectController.class).all()).withSelfRel());
    }

    @PostMapping("/")
    Subject newSubject(@RequestBody Subject newSubject) {
        return repository.save(newSubject);
    }

    @GetMapping("/{id}")
    public EntityModel<Subject> one(@PathVariable Integer id) {
        Subject subject = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return assembler.toModel(subject);
    }

    @PutMapping("/{id}")
    Subject replace(@RequestBody Subject newSubject, @PathVariable Integer id) {
        return repository.findById(id)
                .map(subject -> {
                    subject.setHours(newSubject.getHours());
                    subject.setName(newSubject.getName());
                    return repository.save(subject);
                })
                .orElseGet(() -> {
                    newSubject.setId(id);
                    return repository.save(newSubject);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
