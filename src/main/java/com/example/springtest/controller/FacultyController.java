package com.example.springtest.controller;

import com.example.springtest.controller.assembler.FacultyModelAssembler;
import com.example.springtest.exception.EntityNotFoundException;
import com.example.springtest.model.Faculty;
import com.example.springtest.repository.FacultyRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/faculties")
@RestController
public class FacultyController {
    private final FacultyRepository repository;
    private final FacultyModelAssembler assembler;

    public FacultyController(FacultyRepository repository, FacultyModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Faculty>> all() {
        List<EntityModel<Faculty>> faculties = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(faculties,
                linkTo(methodOn(FacultyController.class).all()).withSelfRel());
    }

    @PostMapping("/")
    Faculty newFaculty(@RequestBody Faculty newFaculty) {
        return repository.save(newFaculty);
    }

    @GetMapping("/{id}")
    public EntityModel<Faculty> one(@PathVariable Integer id) {
        Faculty faculty = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return assembler.toModel(faculty);
    }

    @PutMapping("/{id}")
    Faculty replace(@RequestBody Faculty newFaculty, @PathVariable Integer id) {
        return repository.findById(id)
                .map(faculty -> {
                    faculty.setOfficeAddress(newFaculty.getOfficeAddress());
                    return repository.save(faculty);
                })
                .orElseGet(() -> {
                    newFaculty.setId(id);
                    return repository.save(newFaculty);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
