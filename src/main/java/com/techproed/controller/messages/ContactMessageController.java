package com.techproed.controller.messages;

import com.techproed.entity.concretes.business.ContactMessage;
import com.techproed.service.messages.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact-messages")
public class ContactMessageController {

    private ContactMessageService contactMessageService;

    @PostMapping
    public ResponseEntity<ContactMessage> create(@RequestBody ContactMessage contactMessage) {
        return ResponseEntity.ok(contactMessageService.save(contactMessage));
    }

    @GetMapping
    public ResponseEntity<List<ContactMessage>> getAll() {
        return ResponseEntity.ok(contactMessageService.getAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ContactMessage>> getAllByPage(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sort,
            @RequestParam String type) {

        return ResponseEntity.ok(contactMessageService.getAllByPage(page, size, sort, type));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContactMessage>> searchBySubject(@RequestParam String subject) {
        return ResponseEntity.ok(contactMessageService.getBySubject(subject));
    }

    @GetMapping("/email")
    public ResponseEntity<List<ContactMessage>> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(contactMessageService.getByEmail(email));
    }

    @GetMapping("/date")
    public ResponseEntity<List<ContactMessage>> getByCreationDateBetween(
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {

        return ResponseEntity.ok(contactMessageService.getByCreationDateBetween(startTime, endTime));
    }

    @GetMapping("/time")
    public ResponseEntity<ContactMessage> getByCreationTime(
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime) {

        return ResponseEntity.ok(contactMessageService.getByCreationTime(startTime, endTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        contactMessageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactMessage> update(@RequestBody ContactMessage contactMessage, @PathVariable Long id) {
        return ResponseEntity.ok(contactMessageService.update(contactMessage, id));
    }
}
