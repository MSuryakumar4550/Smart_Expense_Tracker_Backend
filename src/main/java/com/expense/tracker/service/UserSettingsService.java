package com.expense.tracker.service;

import com.expense.tracker.entity.UserSettings;
import com.expense.tracker.repository.UserSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingsService {

    private final UserSettingsRepository repo;

    public UserSettings getSettings(Long userId) {
        return repo.findById(userId)
                .orElse(UserSettings.builder()
                        .userId(userId)
                        .budget(5000.0)
                        .currency("INR")
                        .build());
    }

    public UserSettings saveSettings(
            Long userId, UserSettings settings) {
        settings.setUserId(userId);
        return repo.save(settings);
    }
}