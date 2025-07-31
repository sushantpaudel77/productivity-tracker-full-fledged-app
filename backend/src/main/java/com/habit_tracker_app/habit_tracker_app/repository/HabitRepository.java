package com.habit_tracker_app.habit_tracker_app.repository;

import com.habit_tracker_app.habit_tracker_app.entity.Habit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends MongoRepository<Habit, String> {
}
