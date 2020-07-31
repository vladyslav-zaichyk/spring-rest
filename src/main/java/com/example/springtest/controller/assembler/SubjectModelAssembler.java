package com.example.springtest.controller.assembler;

import com.example.springtest.controller.SubjectController;
import com.example.springtest.model.Subject;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SubjectModelAssembler
        implements RepresentationModelAssembler<Subject, EntityModel<Subject>> {

    @Override
    public EntityModel<Subject> toModel(Subject entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(SubjectController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(SubjectController.class).all()).withRel("subjects"));
    }
}
