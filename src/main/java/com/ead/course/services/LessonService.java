package com.ead.course.services;

import com.ead.course.dtos.LessonRecordDto;
import com.ead.course.models.ModuleModel;
import jakarta.validation.Valid;

import java.util.Optional;

public interface LessonService {
    Object save(LessonRecordDto lessonRecordDto, ModuleModel moduleModel);
}
