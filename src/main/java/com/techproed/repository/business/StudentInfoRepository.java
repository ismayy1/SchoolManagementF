package com.techproed.repository.business;

import com.techproed.entity.concretes.business.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Integer> {
}
