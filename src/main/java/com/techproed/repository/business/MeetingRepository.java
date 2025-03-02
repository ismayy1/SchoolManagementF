package com.techproed.repository.business;

import com.techproed.entity.concretes.business.Meet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meet, Long> {


    List<Meet> findByStudentList_IdEquals(Long studentId);

    List<Meet> getByAdvisoryTeacher_IdEquals(Long studentId);

    Page<Meet> findByAdvisoryTeacher_Id(Long teacherId, Pageable pageable);
}
