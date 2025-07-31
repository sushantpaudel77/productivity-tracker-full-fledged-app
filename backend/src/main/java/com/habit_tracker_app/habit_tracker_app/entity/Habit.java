package com.habit_tracker_app.habit_tracker_app.entity;

import jakarta.validation.constraints.Min;
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
@Document(collection = "habits")
@Builder
public class Habit extends BaseEntity {

    @Id
    private String id;

    @NotBlank(message = "Habit name is required")
    private String name;

    private String description;

    @NotNull(message = "Target frequency is required")
    @Min(value = 1, message = "Target frequency must be at least 1")
    private Integer targetFrequency;

    @Builder.Default
    private List<HabitEntries> entries = new ArrayList<>();
}
