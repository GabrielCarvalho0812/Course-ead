package com.ead.course.services;

import com.ead.course.dtos.LessonRecordDto;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    Object save(LessonRecordDto lessonRecordDto, ModuleModel moduleModel);

    List<LessonModel> findAllLessonsIntoModule(UUID moduleId);

    Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);

    void delete(LessonModel lessonModel);

    Object update(LessonRecordDto lessonRecordDto, LessonModel lessonModel);

    Page<LessonModel> findAllLessonsIntoModule(Specification<LessonModel> spec, Pageable pageable);
}
