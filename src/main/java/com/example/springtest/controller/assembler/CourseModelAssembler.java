package com.example.springtest.controller.assembler;

import com.example.springtest.controller.CourseController;
import com.example.springtest.model.Course;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CourseModelAssembler implements RepresentationModelAssembler<Course, EntityModel<Course>> {

    @Override
    public EntityModel<Course> toModel(Course course) {
        return EntityModel.of(course,
                WebMvcLinkBuilder.linkTo(methodOn(CourseController.class).one(course.getId())).withSelfRel(),
                linkTo(methodOn(CourseController.class).all()).withRel("courses"));
    }
}
