package com.expense.tracker.repository;

import com.expense.tracker.entity.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingsRepository
        extends JpaRepository<UserSettings, Long> {
}