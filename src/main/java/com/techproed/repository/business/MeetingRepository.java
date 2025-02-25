package com.techproed.repository.business;

import com.techproed.entity.concretes.business.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meet, Long> {
}
