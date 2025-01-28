package com.ead.course.controllers;

import com.ead.course.dtos.LessonRecordDto;
import com.ead.course.models.LessonModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<List<LessonModel>> getAllLessons(@PathVariable(value = "moduleId")UUID moduleId){

        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllLessonsIntoModule(moduleId));
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> getOneLesson(@PathVariable(value = "moduleId")UUID moduleId,
                                               @PathVariable(value = "lessonId")UUID lessonId){
        return ResponseEntity.status(HttpStatus.OK).body
                (lessonService.findLessonIntoModule(moduleId, lessonId).get());
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    ResponseEntity<Object> deleteLesson(@PathVariable(value = "moduleId")UUID moduleId,
                                        @PathVariable(value = "lessonId") UUID lessonId){
        lessonService.delete(lessonService.findLessonIntoModule(moduleId, lessonId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully");
    }
}
