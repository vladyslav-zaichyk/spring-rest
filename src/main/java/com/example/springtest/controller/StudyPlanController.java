package com.example.springtest.controller;

import com.example.springtest.controller.assembler.StudyPlanModelAssembler;
import com.example.springtest.exception.EntityNotFoundException;
import com.example.springtest.model.StudyPlan;
import com.example.springtest.repository.StudyPlanRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/studyPlans")
@RestController
public class StudyPlanController {
    private final StudyPlanRepository repository;
    private final StudyPlanModelAssembler assembler;

    public StudyPlanController(StudyPlanRepository repository, StudyPlanModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<StudyPlan>> all() {
        List<EntityModel<StudyPlan>> studyPlans = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(studyPlans,
                linkTo(methodOn(StudyPlanController.class).all()).withSelfRel());
    }

    @PostMapping("/")
    StudyPlan newStudyPlan(@RequestBody StudyPlan newStudyPlan) {
        return repository.save(newStudyPlan);
    }

    @GetMapping("/{id}")
    public EntityModel<StudyPlan> one(@PathVariable Integer id) {
        StudyPlan studyPlan = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return assembler.toModel(studyPlan);
    }

    @PutMapping("/{id}")
    StudyPlan replace(@RequestBody StudyPlan newStudyPlan, @PathVariable Integer id) {
        return repository.findById(id)
                .map(studyPlan -> {
                    studyPlan.setSemester(newStudyPlan.getSemester());
                    studyPlan.setCourses(newStudyPlan.getCourses());
                    return repository.save(studyPlan);
                })
                .orElseGet(() -> {
                    newStudyPlan.setId(id);
                    return repository.save(newStudyPlan);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
