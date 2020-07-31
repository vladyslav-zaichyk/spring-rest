package com.example.springtest.controller.assembler;

import com.example.springtest.controller.FacultyController;
import com.example.springtest.model.Faculty;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FacultyModelAssembler
        implements RepresentationModelAssembler<Faculty, EntityModel<Faculty>> {

    @Override
    public EntityModel<Faculty> toModel(Faculty entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(FacultyController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(FacultyController.class).all()).withRel("faculties"));
    }
}
