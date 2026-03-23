package com.expense.tracker.repository;

import com.expense.tracker.entity.Category;
import com.expense.tracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long UserId);

    List<Expense> findByUserIdAndCategory(Long userId, Category category);

    List<Expense> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

}
