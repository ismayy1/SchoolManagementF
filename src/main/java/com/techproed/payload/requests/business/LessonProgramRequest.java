package com.techproed.payload.requests.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techproed.entity.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonProgramRequest {

    @NotNull(message="Please enter day")
    private Day day;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    @NotNull(message="Please enter start time")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    @NotNull(message="Please enter stop time")
    private LocalTime stopTime;

    @NotNull(message="Please select lesson")
    @Size(min=1, message ="Lesson must not be empty")
    private List<Long> lessonIdList;

    @NotNull(message="Please enter education term")
    private Long educationTermId;
}
