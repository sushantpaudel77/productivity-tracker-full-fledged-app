package com.habit_tracker_app.habit_tracker_app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitEntity {
    private LocalDate date;
    private boolean completed;
    private String notes;
    private LocalDateTime timestamp;
}
