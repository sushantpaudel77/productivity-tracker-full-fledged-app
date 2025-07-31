package com.habit_tracker_app.habit_tracker_app.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "habits")
@Builder
public class Habit extends BaseEntity {

    @Id
    private String id;

    @NotBlank(message = "Habit name is required")
    private String name;

    private String description;

    @NotNull(message = "Target frequency is required")
    private Integer targetFrequency;

    private List<HabitEntity> entries = new ArrayList<>();
}
