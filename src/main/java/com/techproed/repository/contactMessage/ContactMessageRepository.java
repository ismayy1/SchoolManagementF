package com.techproed.repository.contactMessage;

import com.techproed.entity.concretes.business.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    List<ContactMessage> findBySubjectContaining(String subject);
    List<ContactMessage> findByEmail(String email);
    List<ContactMessage> findByLocalDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    ContactMessage findByTimeBetween(LocalTime startTime, LocalTime endTime);
}
