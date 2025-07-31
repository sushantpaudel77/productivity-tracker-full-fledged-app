package com.habit_tracker_app.habit_tracker_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habits")
@CrossOrigin(origins = "*") // Not recommended in prod
@RequiredArgsConstructor
public class HabitController {

}
