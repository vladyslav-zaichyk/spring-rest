package com.example.springtest.controller;

import com.example.springtest.controller.assembler.CourseModelAssembler;
import com.example.springtest.exception.EntityNotFoundException;
import com.example.springtest.model.Course;
import com.example.springtest.repository.CourseRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/courses")
@RestController
public class CourseController {
    private final CourseRepository repository;
    private final CourseModelAssembler assembler;

    public CourseController(CourseRepository repository, CourseModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public CollectionModel<EntityModel<Course>> all()  {
        List<EntityModel<Course>> courses = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(courses,
                linkTo(methodOn(CourseController.class).all()).withSelfRel());
    }

    @PostMapping("/")
    Course newCourse(@RequestBody Course newCourse) {
        return repository.save(newCourse);
    }

    @GetMapping("/{id}")
    public EntityModel<Course> one(@PathVariable Integer id) {
        Course course = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return assembler.toModel(course);
    }

    @PutMapping("/{id}")
    Course replace(@RequestBody Course newCourse, @PathVariable Integer id) {
        return repository.findById(id)
                .map(course -> {
                    course.setName(newCourse.getName());
                    course.setSubject(newCourse.getSubject());
                    course.setInstructor(newCourse.getInstructor());
                    return repository.save(course);
                })
                .orElseGet(() -> {
                    newCourse.setId(id);
                    return repository.save(newCourse);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

}
