package com.expense.tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SummaryResponse {

    private Double totalSpent;
    private Double monthlyBudget;
    private Double remaining;
    private boolean budgetExceeded;
    private Double overspentBy;
    private Map<String, Double> categoryBreakdown;
}