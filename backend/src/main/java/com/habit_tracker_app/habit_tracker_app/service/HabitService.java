package com.habit_tracker_app.habit_tracker_app.service;

import com.habit_tracker_app.habit_tracker_app.entity.Habit;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HabitService {

    List<Habit> getAllHabits();

    Optional<Habit> getHabitById(String id);

    Habit createHabit(Habit habit);

    Optional<Habit> updateHabit(String id, Habit habitDetails);

    boolean deleteHabit(String id);

    Optional<Habit> addHabitEntry(String habitId, LocalDate date, boolean completed, String notes);

    List<Habit> searchHabits(String name);
}
