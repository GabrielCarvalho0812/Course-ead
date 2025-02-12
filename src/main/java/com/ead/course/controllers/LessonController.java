package com.ead.course.controllers;

import com.ead.course.dtos.LessonRecordDto;
import com.ead.course.models.LessonModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
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
public class LessonController {

    Logger logger = LogManager.getLogger(LessonController.class);

    private final LessonService lessonService;;
    private final ModuleService moduleService;

    public LessonController(LessonService lessonService, ModuleService moduleService) {
        this.lessonService = lessonService;
        this.moduleService = moduleService;
    }

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> saveLesson(@PathVariable(value = "moduleId")UUID moduleId,
                                             @RequestBody @Valid LessonRecordDto lessonRecordDto){
        logger.debug("POST saveLesson lessonRecordDto received {} ", lessonRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body
                (lessonService.save(lessonRecordDto, moduleService.findById(moduleId).get()));
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Page<LessonModel>> getAllLessons(@PathVariable(value = "moduleId")UUID moduleId,
                                                           SpecificationTemplate.LessonSpec spec,
                                                           Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body
                (lessonService.findAllLessonsIntoModule(SpecificationTemplate.lessonModuleId(moduleId).and(spec),pageable));
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
        logger.debug("DELETE deleteLesson lessonId received {} ", lessonId);
        lessonService.delete(lessonService.findLessonIntoModule(moduleId, lessonId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully");
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    ResponseEntity<Object> updateLesson(@PathVariable(value = "moduleId")UUID moduleId,
                                        @PathVariable(value = "lessonId")UUID lessonId,
                                        @RequestBody @Valid LessonRecordDto lessonRecordDto){
        logger.debug("PUT updateLesson lessonRecordDto received {} ", lessonRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body
                (lessonService.update(lessonRecordDto,lessonService.findLessonIntoModule(moduleId, lessonId).get()));
    }
}
