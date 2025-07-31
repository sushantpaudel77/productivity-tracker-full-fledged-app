package com.habit_tracker_app.habit_tracker_app.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
