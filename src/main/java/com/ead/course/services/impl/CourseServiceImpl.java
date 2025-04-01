package com.ead.course.services.impl;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.dtos.CourseRecordDto;
import com.ead.course.exceptions.NotFoundException;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    final CourseRepository courseRepository;
    final ModuleRepository moduleRepository;
    final LessonRepository lessonRepository;
    final CourseUserRepository courseUserRepository;
    final AuthUserClient authUserClient;

    public CourseServiceImpl(CourseRepository courseRepository, ModuleRepository moduleRepository, LessonRepository lessonRepository, CourseUserRepository courseUserRepository, AuthUserClient authUserClient) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.courseUserRepository = courseUserRepository;
        this.authUserClient = authUserClient;
    }

    @Transactional // rouback para garantir que todos esses processos sejam realizados ,para que naõ fique pela metade
    @Override
    public void delete(CourseModel courseModel) {
        boolean deleteCourseUserInAuthuser = false;
        List<ModuleModel> moduleModelList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
        if (!moduleModelList.isEmpty()){ //si ela esta vazia
            for (ModuleModel module : moduleModelList) { // se ela não estiver vazia
                List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (!lessonModelList.isEmpty()){
                    lessonRepository.deleteAll(lessonModelList);
                }
            }
            moduleRepository.deleteAll(moduleModelList);
        }

        List<CourseUserModel> courseUserModelList =
                courseUserRepository.findAllCourseUserIntoCourse(courseModel.getCourseId());

        if (!courseUserModelList.isEmpty()){
            courseUserRepository.deleteAll(courseUserModelList);
            deleteCourseUserInAuthuser = true;
        }
        courseRepository.delete(courseModel);
        if (deleteCourseUserInAuthuser){
            authUserClient.deleteCourseUserInAuthuser(courseModel.getCourseId());
        }
    }

    @Override
    public CourseModel save(CourseRecordDto courseRecordDto) {
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseRecordDto, courseModel); //fazendo a conversão dto para models
        courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(courseModel);
    }

    @Override
    public boolean existsByName(String name) {
        return courseRepository.existsByName(name);
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        Optional<CourseModel> courseModelOptional = courseRepository.findById(courseId);
        if (courseModelOptional.isEmpty()){
            throw new NotFoundException("Error: Course not found");
        }
        return courseModelOptional;
    }

    @Override
    public CourseModel update(CourseRecordDto courseRecordDto, CourseModel courseModel) {
        BeanUtils.copyProperties(courseRecordDto, courseModel); //fazendo a conversão de DTO para Model
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC"))); // mudar a data de ultima atualização
         return courseRepository.save(courseModel);
    }

}
