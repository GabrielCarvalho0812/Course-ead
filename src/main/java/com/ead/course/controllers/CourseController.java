package com.ead.course.controllers;

import com.ead.course.dtos.CourseRecordDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;
import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    Logger logger = LogManager.getLogger(CourseController.class);

    final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody @Valid CourseRecordDto courseRecordDto){
        logger.debug("POST saveCourse courseRecordDto received {} ", courseRecordDto);
        if (courseService.existsByName(courseRecordDto.name())){
            logger.warn("Course Name {} is Already Taken ", courseRecordDto.name());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseRecordDto));

    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAllCourses(SpecificationTemplate.CourseSpec spec,
                                                           Pageable pageable,
                                                           @RequestParam(required = false) UUID userId){
        Page<CourseModel> courseModelPage = (userId != null)
                ? courseService.findAll(SpecificationTemplate.courseUserId(userId).and(spec), pageable)
                : courseService.findAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(spec, pageable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findById(courseId).get());
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId){
        logger.debug("DELETE deleteCourse courseId received {} ", courseId);
        courseService.delete(courseService.findById(courseId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully");

    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse (@PathVariable(value = "courseId") UUID courseId,
                                                @RequestBody @Valid CourseRecordDto courseRecordDto){
        logger.debug("PUT updateCourse courseRecordDto received {} ", courseRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body
                (courseService.update(courseRecordDto, courseService.findById(courseId).get()));

    }


}
