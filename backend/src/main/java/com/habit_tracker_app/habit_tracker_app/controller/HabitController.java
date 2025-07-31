package com.habit_tracker_app.habit_tracker_app.controller;

import com.habit_tracker_app.habit_tracker_app.entity.Habit;
import com.habit_tracker_app.habit_tracker_app.service.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/habits")
@CrossOrigin(origins = "*") // Not recommended in prod
@RequiredArgsConstructor
@Slf4j
public class HabitController {


    private final HabitService habitService;

    @GetMapping
    public ResponseEntity<List<Habit>> getAllHabits() {
        log.info("GET /api/habits - Fetching all habits");
        List<Habit> habits = habitService.getAllHabits();
        return ResponseEntity.ok(habits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habit> getHabitById(@PathVariable String id) {
        log.info("GET /api/habits/{} - Fetching habit by id", id);
        return habitService.getHabitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Habit> createHabit(@Valid @RequestBody Habit habit) {
        log.info("POST /api/habits - Creating new habit: {}", habit.getName());
        Habit createdHabit = habitService.createHabit(habit);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHabit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habit> updateHabit(@PathVariable String id, @Valid @RequestBody Habit habitDetails) {
        log.info("PUT /api/habits/{} - Updating habit", id);
        return habitService.updateHabit(id, habitDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable String id) {
        log.info("DELETE /api/habits/{} - Deleting habit", id);
        if (habitService.deleteHabit(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/entries")
    public ResponseEntity<Habit> addHabitEntry(
            @PathVariable String id,
            @RequestParam LocalDate date,
            @RequestParam boolean completed,
            @RequestParam(required = false) String notes) {
        log.info("POST /api/habits/{}/entries - Adding entry for date: {}", id, date);
        return habitService.addHabitEntry(id, date, completed, notes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Habit>> searchHabits(@RequestParam String name) {
        log.info("GET /api/habits/search - Searching habits with name: {}", name);
        List<Habit> habits = habitService.searchHabits(name);
        return ResponseEntity.ok(habits);
    }
}
