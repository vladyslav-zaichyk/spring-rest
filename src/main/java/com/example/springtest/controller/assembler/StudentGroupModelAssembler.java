package com.example.springtest.controller.assembler;

import com.example.springtest.controller.StudentGroupController;
import com.example.springtest.model.StudentGroup;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StudentGroupModelAssembler
        implements RepresentationModelAssembler<StudentGroup, EntityModel<StudentGroup>> {

    @Override
    public EntityModel<StudentGroup> toModel(StudentGroup entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(StudentGroupController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(StudentGroupController.class).all()).withRel("groups"));
    }
}
