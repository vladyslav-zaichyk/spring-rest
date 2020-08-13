package com.example.springtest.controller.assembler;

import com.example.springtest.controller.StudentController;
import com.example.springtest.model.Student;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StudentModelAssembler
        implements RepresentationModelAssembler<Student, EntityModel<Student>> {

    @Override
    public EntityModel<Student> toModel(Student entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(StudentController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).all()).withRel("students"));
    }
}
