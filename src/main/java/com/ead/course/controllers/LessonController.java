package com.ead.course.controllers;

import com.ead.course.dtos.LessonRecordDto;
import com.ead.course.services.CourseService;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class LessonController {

    private final LessonService lessonService;;
    private final ModuleService moduleService;

    public LessonController(LessonService lessonService, ModuleService moduleService) {
        this.lessonService = lessonService;
        this.moduleService = moduleService;
    }

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> saveLesson(@PathVariable(value = "moduleId")UUID moduleId,
                                             @RequestBody @Valid LessonRecordDto lessonRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body
                (lessonService.save(lessonRecordDto, moduleService.findById(moduleId).get()));

    }
}
