package com.ead.course.controllers;

import com.ead.course.dtos.ModuleRecordDto;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.CourseService;
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
public class ModuleController {

    Logger logger = LogManager.getLogger(ModuleController.class);

    final ModuleService moduleService;
    final CourseService courseService;

    public ModuleController(ModuleService moduleService, CourseService courseService) {
        this.moduleService = moduleService;
        this.courseService = courseService;
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> saveModule(@PathVariable(value = "courseId") UUID courseId,
                                            @RequestBody @Valid ModuleRecordDto moduleRecordDto){
        logger.debug("POST saveModule moduleRecordDto received {} ", moduleRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(moduleService.save(moduleRecordDto, courseService.findById(courseId).get()));
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<Page<ModuleModel>> getAllModules(@PathVariable(value = "courseId") UUID courseId,
                                                           SpecificationTemplate.ModuleSpec spec, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body
                (moduleService.findAllModulesIntoCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getOneModule(@PathVariable(value = "courseId")UUID courseId,
                                               @PathVariable(value = "moduleId")UUID moduleId){
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findModuleIntoCourse(courseId, moduleId));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteModule(@PathVariable(value = "courseId")UUID courseId,
                                               @PathVariable(value = "moduleId")UUID moduleId){
        logger.debug("DELETE deleteModule moduleId received {} ", moduleId);
        moduleService.delete(moduleService.findModuleIntoCourse(courseId, moduleId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully.");
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> updateModule(@PathVariable(value = "courseId")UUID courseId,
                                               @PathVariable(value = "moduleId")UUID moduleId,
                                               @RequestBody @Valid ModuleRecordDto moduleRecordDto){
        logger.debug("PUT updateModule moduleRecordDto received {} ", moduleRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body
                (moduleService.update(moduleRecordDto, moduleService.findModuleIntoCourse(courseId ,moduleId).get()));

    }

}
