package com.habit_tracker_app.habit_tracker_app.service;

import com.habit_tracker_app.habit_tracker_app.entity.Habit;
import com.habit_tracker_app.habit_tracker_app.entity.HabitEntity;
import com.habit_tracker_app.habit_tracker_app.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;

    @Override
    public List<Habit> getAllHabits() {
        log.info("Fetching all habits");
        return habitRepository.findAll();
    }

    @Override
    public Optional<Habit> getHabitById(String id) {
        log.info("Fetching habit with id: {}", id);
        return habitRepository.findById(id);
    }

    @Override
    public Habit createHabit(Habit habit) {
        log.info("Creating new habit: {}", habit.getName());
        habit.setCreatedAt(LocalDateTime.now());
        habit.setUpdatedAt(LocalDateTime.now());
        return habitRepository.save(habit);
    }

    @Override
    public Optional<Habit> updateHabit(String id, Habit habitDetails) {
        log.info("Updating habit with id: {}", id);
        return habitRepository.findById(id)
                .map(habit -> {
                    habit.setName(habitDetails.getName());
                    habit.setDescription(habitDetails.getDescription());
                    habit.setTargetFrequency(habitDetails.getTargetFrequency());
                    habit.setUpdatedAt(habitDetails.getUpdatedAt());
                    return habitRepository.save(habit);
                });
    }

    @Override
    public boolean deleteHabit(String id) {
        log.info("Deleting habit with id: {}", id);
        if (habitRepository.existsById(id)) {
            habitRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Habit> addHabitEntry(String habitId, LocalDate date, boolean completed, String notes) {
        log.info("Adding entry for habit: {} on date: {}", habitId, date);
        return habitRepository.findById(habitId)
                .map(habit -> {
                    // remove existing entry for the same date if exists
                    habit.getEntries().removeIf(entry -> entry.getDate().equals(date));

                    // add new entry
                    HabitEntity entry = new HabitEntity();
                    entry.setDate(date);
                    entry.setCompleted(completed);
                    entry.setNotes(notes);
                    entry.setTimestamp(LocalDateTime.now());

                    habit.getEntries().add(entry);
                    habit.setUpdatedAt(LocalDateTime.now());

                    return habitRepository.save(habit);
                });
    }

    @Override
    public List<Habit> searchHabits(String name) {
        log.info("Searching habits with name containing: {}", name);
        return habitRepository.findByNameContainingIgnoreCase(name);
    }
}
