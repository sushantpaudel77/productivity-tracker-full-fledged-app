package com.habit_tracker_app.habit_tracker_app.repository;

import com.habit_tracker_app.habit_tracker_app.entity.Habit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HabitRepository extends MongoRepository<Habit, String> {
    List<Habit> findByNameContainingIgnoreCase(String name);

    @Query("{ 'createdAt' : { $gte: ?0, $lte: ?1 } }")
    List<Habit> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("{ 'entries.date' : ?0 }")
    List<Habit> findByEntryDate(String date);
}
