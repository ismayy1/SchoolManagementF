package com.techproed.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_contactMessage")
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name can't be null")
    private String name;

    @NotNull(message = "Email can't be null")
    @Email(message = "Email should be valid!")
    private String email;

    @NotNull(message = "Subject can't be null")
    private String subject;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm", timezone = "US")
    @Setter(AccessLevel.NONE)
    private LocalDateTime localDateTime = LocalDateTime.now();
}
