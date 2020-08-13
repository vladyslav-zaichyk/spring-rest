package com.example.springtest.controller.assembler;

import com.example.springtest.controller.StudyPlanController;
import com.example.springtest.model.StudyPlan;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StudyPlanModelAssembler
        implements RepresentationModelAssembler<StudyPlan, EntityModel<StudyPlan>> {

    @Override
    public EntityModel<StudyPlan> toModel(StudyPlan entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(StudyPlanController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(StudyPlanController.class).all()).withRel("studyplans"));
    }
}
