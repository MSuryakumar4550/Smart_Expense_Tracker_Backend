package com.expense.tracker.controller;

import com.expense.tracker.entity.UserSettings;
import com.expense.tracker.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserSettingsController {

    private final UserSettingsService service;

    @GetMapping("/{userId}")
    public ResponseEntity<UserSettings> getSettings(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
            service.getSettings(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<UserSettings> saveSettings(
            @PathVariable Long userId,
            @RequestBody UserSettings settings) {
        return ResponseEntity.ok(
            service.saveSettings(userId, settings));
    }
}