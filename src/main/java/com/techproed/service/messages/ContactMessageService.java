package com.techproed.service.messages;

import com.techproed.entity.concretes.business.ContactMessage;
import com.techproed.repository.contactMessage.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public List<ContactMessage> getAll() {
        return contactMessageRepository.findAll();
    }


    public ContactMessage save(ContactMessage contactMessage) {
        return contactMessageRepository.save(contactMessage);
    }

    public Page<ContactMessage> getAllByPage(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));
        return contactMessageRepository.findAll(pageable);
    }

    public List<ContactMessage> getBySubject(String subject) {
        return contactMessageRepository.findBySubjectContaining(subject);
    }


    public List<ContactMessage> getByEmail(String email) {
        return contactMessageRepository.findByEmail(email);
    }

    public List<ContactMessage> getByCreationDateBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return contactMessageRepository.findByLocalDateTimeBetween(startTime, endTime);
    }

    public ContactMessage getByCreationTime(LocalTime startTime, LocalTime endTime) {
        return contactMessageRepository.findByTimeBetween(startTime, endTime);
    }

    public void deleteById(Long id) {
        contactMessageRepository.deleteById(id);
    }

    public ContactMessage update(ContactMessage contactMessage, Long id) {
        ContactMessage existingMessage = contactMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact Message Not Found!"));

        existingMessage.setName(contactMessage.getName());
        existingMessage.setSubject(contactMessage.getSubject());
        existingMessage.setEmail(contactMessage.getEmail());
        return contactMessageRepository.save(existingMessage);
    }
}
