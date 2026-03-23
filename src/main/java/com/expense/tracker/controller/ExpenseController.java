package com.expense.tracker.controller;

import com.expense.tracker.dto.SummaryResponse;
import com.expense.tracker.entity.Category;
import com.expense.tracker.entity.Expense;
import com.expense.tracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> addExpense(
            @RequestBody @Valid Expense expense) {
        return ResponseEntity.ok(
                expenseService.addExpense(expense));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Expense>> getAllExpenses(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
                expenseService.getAllExpenses(userId));
    }

    @GetMapping("/{userId}/category/{category}")
    public ResponseEntity<List<Expense>> getByCategory(
            @PathVariable Long userId,
            @PathVariable Category category) {
        return ResponseEntity.ok(
                expenseService.getByCategory(userId, category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable Long id,
            @RequestBody @Valid Expense expense) {
        return ResponseEntity.ok(
                expenseService.updateExpense(id, expense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(
            @PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok(
                "Expense deleted successfully");
    }

    @GetMapping("/{userId}/summary")
    public ResponseEntity<SummaryResponse> getMonthlySummary(
            @PathVariable Long userId,
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam Double budget) {

        return ResponseEntity.ok(
                expenseService.getMonthlySummary(
                        userId, month, year, budget));
    }
}