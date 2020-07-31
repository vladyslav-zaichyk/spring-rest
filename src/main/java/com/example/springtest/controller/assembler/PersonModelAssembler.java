package com.example.springtest.controller.assembler;

import com.example.springtest.controller.PersonController;
import com.example.springtest.model.Person;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonModelAssembler
        implements RepresentationModelAssembler<Person, EntityModel<Person>> {

    @Override
    public EntityModel<Person> toModel(Person entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(PersonController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(PersonController.class).all()).withRel("persons"));
    }
}
