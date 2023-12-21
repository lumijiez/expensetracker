package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;
    @Autowired
    private UserRepository userRepository;
    private User user;
    private ExpenseCategory expenseCategory;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .firstName("Test")
                .lastName("TestLast")
                .username("UserTest")
                .incomes(new ArrayList<>())
                .expenses(new ArrayList<>())
                .build();

        userRepository.save(user);

        expenseCategory = expenseCategoryRepository.getReferenceById(1L);
    }

    @Test
    public void ExpenseRepository_SaveAll_ReturnExpense() {

        Expense expense = Expense.builder()
                .user(user)
                .amount(BigDecimal.valueOf(77))
                .category(expenseCategory)
                .date(LocalDate.now())
                .build();

        Expense savedExpense = expenseRepository.save(expense);

        Assertions.assertThat(savedExpense).isNotNull();
        Assertions.assertThat(savedExpense.getId()).isGreaterThan(0L);
    }

    @Test
    public void ExpenseRepository_GateAll_ReturnsMoreThenOneExpense() {

        List<Expense> expenseList = expenseRepository.findAll();
        int qtyBefore = expenseList.size();

        Expense expense1 = Expense.builder()
                .user(user)
                .amount(BigDecimal.valueOf(77))
                .category(expenseCategory)
                .date(LocalDate.now())
                .build();
        Expense expense2 = Expense.builder()
                .user(user)
                .amount(BigDecimal.valueOf(177))
                .category(expenseCategory)
                .date(LocalDate.now())
                .build();

        expenseRepository.save(expense1);
        expenseRepository.save(expense2);

        expenseList = expenseRepository.findAll();

        Assertions.assertThat(expenseList).isNotNull();
        assertThat(expenseList).hasSize(qtyBefore + 2);
    }

    @Test
    public void ExpenseRepository_FindById_ReturnExpense() {

        Expense expense = Expense.builder()
                .user(user)
                .amount(BigDecimal.valueOf(77))
                .category(expenseCategory)
                .date(LocalDate.now())
                .build();

        expenseRepository.save(expense);

        Optional<Expense> expenseReturn = expenseRepository.findById(expense.getId());
        Assertions.assertThat(expenseReturn.isPresent()).isTrue();
    }

    @Test
    public void ExpenseRepository_FindByUser_ReturnExpenses() {

        List<Expense> expenses = expenseRepository.findByUser(user);

        Assertions.assertThat(expenses).isNotNull();
    }

    @Test
    public void ExpenseRepository_FindByDate_ReturnExpenses() {

        List<Expense> expenses = expenseRepository.findByDate(LocalDate.of(2023, 10, 7));

        Assertions.assertThat(expenses).isNotNull();
    }

    @Test
    public void ExpenseRepository_UpdateExpense_ReturnExpenseNotNull() {

        ExpenseCategory expenseCategory = expenseCategoryRepository.getReferenceById(4L);

        Expense expense = Expense.builder()
                .user(user)
                .amount(BigDecimal.valueOf(700))
                .category(expenseCategory)
                .date(LocalDate.of(2023, 10, 5))
                .build();

        expenseRepository.save(expense);

        Optional<Expense> expenseSave = expenseRepository.findById(expense.getId());
        Assertions.assertThat(expenseSave).isNotNull();

        Expense expenseToUpdate = expenseSave.get();
        expenseToUpdate.setDate(LocalDate.of(2023, 10, 6));
        expenseToUpdate.setAmount(BigDecimal.valueOf(777));

        Expense updatedExpense = expenseRepository.save(expenseToUpdate);

        Assertions.assertThat(updatedExpense).isNotNull();
        Assertions.assertThat(updatedExpense.getAmount()).isEqualTo(BigDecimal.valueOf(777));
    }

    @Test
    public void ExpenseRepository_DeleteExpense_ReturnExpenseNull() {

        ExpenseCategory expenseCategory = expenseCategoryRepository.getReferenceById(4L);

        Expense expense = Expense.builder()
                .user(user)
                .amount(BigDecimal.valueOf(700))
                .category(expenseCategory)
                .date(LocalDate.of(2023, 10, 5))
                .build();

        expenseRepository.save(expense);
        expenseRepository.deleteById(expense.getId());

        Optional<Expense> deletedExpense = expenseRepository.findById(expense.getId());

        Assertions.assertThat(deletedExpense.isPresent()).isFalse();
    }
}
