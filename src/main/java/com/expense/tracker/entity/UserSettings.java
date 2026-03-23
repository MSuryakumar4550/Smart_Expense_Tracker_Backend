package com.expense.tracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSettings {

    @Id
    private Long userId;

    private Double budget;
    private String currency;
}