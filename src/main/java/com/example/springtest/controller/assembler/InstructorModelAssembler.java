package com.example.springtest.controller.assembler;

import com.example.springtest.controller.InstructorController;
import com.example.springtest.model.Instructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InstructorModelAssembler
        implements RepresentationModelAssembler<Instructor, EntityModel<Instructor>> {

    @Override
    public EntityModel<Instructor> toModel(Instructor entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(InstructorController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(InstructorController.class).all()).withRel("instructors"));
    }
}
