package com.techproed.repository.business;

import com.techproed.entity.concretes.business.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Integer> {

    @Query("SELECT (COUNT (s)>0) FROM StudentInfo s WHERE s.student.id = ?1 AND s.lesson.lessonName = ?2")
    boolean isStudentInfoExistForLesson(Long studentId, String lessonName);
}
