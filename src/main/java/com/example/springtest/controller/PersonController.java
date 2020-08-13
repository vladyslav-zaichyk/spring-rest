package com.example.springtest.controller;

import com.example.springtest.controller.assembler.PersonModelAssembler;
import com.example.springtest.exception.EntityNotFoundException;
import com.example.springtest.model.Person;
import com.example.springtest.repository.PersonRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/person")
@RestController
public class PersonController {
    private final PersonRepository repository;
    private final PersonModelAssembler assembler;

    public PersonController(PersonRepository repository, PersonModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Person>> all() {
        List<EntityModel<Person>> persons = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(persons,
                linkTo(methodOn(PersonController.class).all()).withSelfRel());
    }

    @PostMapping("/")
    Person newPerson(@RequestBody Person newPerson) {
        return repository.save(newPerson);
    }

    @GetMapping("/{id}")
    public EntityModel<Person> one(@PathVariable Integer id) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return assembler.toModel(person);
    }

    @PutMapping("/{id}")
    Person replace(@RequestBody Person newPerson, @PathVariable Integer id) {
        return repository.findById(id)
                .map(person -> {
                    person.setBirthDate(newPerson.getBirthDate());
                    person.setFirstName(newPerson.getFirstName());
                    person.setLastName(newPerson.getLastName());
                    person.setMiddleName(newPerson.getMiddleName());
                    person.setPhone(newPerson.getPhone());
                    person.setSex(newPerson.getSex());
                    return repository.save(person);
                })
                .orElseGet(() -> {
                    newPerson.setId(id);
                    return repository.save(newPerson);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
