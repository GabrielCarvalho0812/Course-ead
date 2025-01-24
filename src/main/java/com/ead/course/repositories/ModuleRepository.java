package com.ead.course.repositories;

import com.ead.course.models.ModuleModel;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel>{

//    @EntityGraph(attributePaths = {"course"})
//    ModuleModel findAllByTitle(String title);

    @Query(value="select * from tb_modules where course_course_id = :courseId", nativeQuery = true)
    List<ModuleModel> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

}
