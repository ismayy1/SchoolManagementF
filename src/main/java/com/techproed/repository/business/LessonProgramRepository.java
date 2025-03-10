package com.techproed.repository.business;

import com.techproed.entity.concretes.business.LessonProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LessonProgramRepository extends JpaRepository<LessonProgram, Long> {

    List<LessonProgram> findByUsers_IdNull();

    List<LessonProgram> findByUsers_IdNotNull();
}
