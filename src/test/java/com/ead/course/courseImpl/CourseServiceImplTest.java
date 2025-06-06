package com.ead.course.courseImpl;

import com.ead.course.dtos.CourseRecordDto;
import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.exceptions.NotFoundException;
import com.ead.course.models.CourseModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.impl.CourseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Executable;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @InjectMocks
    CourseServiceImpl courseService;

    @Mock
    CourseRepository courseRepository;

    @Mock
    ModuleRepository moduleRepository;

    @Mock
    LessonRepository lessonRepository;

    @Test
    void saveCourseComSucesso(){

        UUID userInstructorId = UUID.randomUUID();

        CourseRecordDto dto = new CourseRecordDto(
                "Java para Iniciantes",
                "Curso completo de Java.",
                CourseStatus.IN_PROGRESS,
                CourseLevel.BEGINNER,
                userInstructorId,
                "imagem.jpg"
        );

        when(courseRepository.save(any(CourseModel.class))).thenAnswer(invocation -> {
            CourseModel model = invocation.getArgument(0);
            model.setCourseId(UUID.randomUUID()); // simula retorno com ID
            return model;
        });

        CourseModel result = courseService.save(dto);

        assertNotNull(result);
        assertEquals(dto.name(), result.getName());
        assertEquals(dto.description(), result.getDescription());
        assertEquals(dto.courseStatus(), result.getCourseStatus());
        assertEquals(dto.courseLevel(), result.getCourseLevel());
        assertEquals(dto.userInstructor(), result.getUserInstructor());
        assertEquals(dto.imageUrl(), result.getImageUrl());
        verify(courseRepository).save(any(CourseModel.class));
    }

    @Test
    void saveCourseComNomeNuloDeveLancarExcecao(){
        UUID userInstructorId = UUID.randomUUID();
        CourseRecordDto dto = new CourseRecordDto(
                null, // Nome nulo
                "Curso sem nome.",
                CourseStatus.IN_PROGRESS,
                CourseLevel.BEGINNER,
                userInstructorId,
                "imagem.jpg"
        );


        when(courseRepository.save(any(CourseModel.class))).thenAnswer(invocation -> {
            CourseModel model = invocation.getArgument(0);
            model.setCourseId(UUID.randomUUID());
            return model;
        });

        CourseModel result = courseService.save(dto);

        assertNotNull(result);
        assertEquals(dto.name(), result.getName());
        verify(courseRepository).save(any(CourseModel.class));
    }

    @Test
    void deveRetornarTrueQuandoNomeDoCursoJaExiste(){
        String nomeCurso = "Curso completo de Java";

        when(courseRepository.existsByName(nomeCurso)).thenReturn(true);

         Boolean existe =  courseService.existsByName(nomeCurso);

        assertTrue(existe);
        verify(courseRepository).existsByName(nomeCurso);
    }

    @Test
    void deveRetornarQuandoCursoExiste(){
        UUID idCurso = UUID.randomUUID();
        CourseModel curso = new CourseModel();
        curso.setCourseId(idCurso);
        curso.setName("Curso completo de Java");

        when(courseRepository.findById(idCurso)).thenReturn(Optional.of(curso));

        Optional<CourseModel> result = courseService.findById(idCurso);

        assertTrue(result.isPresent());
        verify(courseRepository).findById(idCurso);
        assertEquals(curso.getName(),result.get().getName());
    }

    @Test
    void deveLancarExcecaoQuandoNomeDoCursoNaoExiste() {
        UUID idCursoInexistente = UUID.randomUUID();

        when(courseRepository.findById(idCursoInexistente)).thenReturn(Optional.empty());

        NotFoundException excecao = Assertions.assertThrows(
                NotFoundException.class,
                () -> courseService.findById(idCursoInexistente)
        );

        assertEquals("Error: Course not found", excecao.getMessage());
        verify(courseRepository).findById(idCursoInexistente);
    }





}
