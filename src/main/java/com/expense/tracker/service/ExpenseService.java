package com.expense.tracker.service;

import com.expense.tracker.dto.SummaryResponse;
import com.expense.tracker.entity.Category;
import com.expense.tracker.entity.Expense;
import com.expense.tracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    public List<Expense> getByCategory(
            Long userId, Category category) {
        return expenseRepository
                .findByUserIdAndCategory(userId, category);
    }

    public Expense updateExpense(
            Long id, Expense updatedExpense) {
        Expense existing = expenseRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Expense not found with id: " + id));
        existing.setTitle(updatedExpense.getTitle());
        existing.setAmount(updatedExpense.getAmount());
        existing.setCategory(updatedExpense.getCategory());
        existing.setDescription(
                updatedExpense.getDescription());
        existing.setDate(updatedExpense.getDate());
        return expenseRepository.save(existing);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public SummaryResponse getMonthlySummary(
            Long userId, int month, int year,
            Double monthlyBudget) {

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(
                start.lengthOfMonth());

        List<Expense> expenses =
                expenseRepository.findByUserIdAndDateBetween(
                        userId, start, end);

        Double totalSpent = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        Map<String, Double> breakdown = expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCategory().name(),
                        Collectors.summingDouble(Expense::getAmount)
                ));

        boolean exceeded = totalSpent > monthlyBudget;
        Double overspentBy = exceeded
                ? totalSpent - monthlyBudget : 0.0;

        return SummaryResponse.builder()
                .totalSpent(totalSpent)
                .monthlyBudget(monthlyBudget)
                .remaining(monthlyBudget - totalSpent)
                .budgetExceeded(exceeded)
                .overspentBy(overspentBy)
                .categoryBreakdown(breakdown)
                .build();
    }
}

