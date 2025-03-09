package com.techproed.payload.mappers;

import com.techproed.entity.concretes.business.ContactMessage;
import com.techproed.payload.requests.business.ContactMessageRequest;
import com.techproed.payload.response.business.ContactMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContactMessageMapper {

    public ContactMessageResponse mapToResponse(ContactMessage contactMessage) {
        return ContactMessageResponse.builder()
                .name(contactMessage.getName())
                .email(contactMessage.getEmail())
                .subject(contactMessage.getSubject())
                .message(contactMessage.getMessage())
                .createdAt(contactMessage.getCreatedAt())
                .build();
    }

    public ContactMessage mapToEntity(ContactMessageRequest contactMessageRequest) {
        return ContactMessage.builder()
                .name(contactMessageRequest.getName())
                .email(contactMessageRequest.getEmail())
                .subject(contactMessageRequest.getSubject())
                .message(contactMessageRequest.getMessage())
                .build();
    }

    public List<ContactMessageResponse> mapToResponseList(List<ContactMessage> allMessages) {
        return allMessages.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}