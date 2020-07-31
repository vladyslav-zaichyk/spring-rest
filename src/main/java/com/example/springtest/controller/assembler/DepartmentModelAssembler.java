package com.example.springtest.controller.assembler;

import com.example.springtest.controller.DepartmentController;
import com.example.springtest.model.Department;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DepartmentModelAssembler
        implements RepresentationModelAssembler<Department, EntityModel<Department>> {

    @Override
    public EntityModel<Department> toModel(Department entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(DepartmentController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(DepartmentController.class).all()).withRel("departments"));
    }
}
