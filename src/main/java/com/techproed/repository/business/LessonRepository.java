package com.techproed.repository.business;

import com.techproed.entity.concretes.business.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LessonRepository extends JpaRepository<Lesson, Long> {
}
