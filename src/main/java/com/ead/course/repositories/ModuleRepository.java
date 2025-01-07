package com.ead.course.repositories;

import com.ead.course.models.ModuleModel;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {

//    @EntityGraph(attributePaths = {"course"})
//    ModuleModel findAllByTitle(String title);

    @Modifying
    @Query(value = "select * from tb_modules where course_course_id = ;courseId" , nativeQuery = true)
    List<ModuleModel> findAllByModulesIntoCourse(@Param("courseId") UUID courseId);
}
