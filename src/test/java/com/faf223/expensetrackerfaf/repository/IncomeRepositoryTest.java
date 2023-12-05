package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.model.IncomeCategory;
import com.faf223.expensetrackerfaf.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IncomeRepositoryTest {

    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void IncomeRepository_SaveAll_ReturnIncome() {

        User user = User.builder()
                .firstName("Test")
                .lastName("TestLast")
                .username("UserTest")
                .incomes(new ArrayList<>())
                .expenses(new ArrayList<>())
                .build();

        userRepository.save(user);
        IncomeCategory incomeCategory = incomeCategoryRepository.getReferenceById(1L);

        Income income = Income.builder()
                .user(user)
                .amount(BigDecimal.valueOf(77))
                .category(incomeCategory)
                .date(LocalDate.now())
                .build();

        Income savedIncome = incomeRepository.save(income);

        Assertions.assertThat(savedIncome).isNotNull();
        Assertions.assertThat(savedIncome.getId()).isGreaterThan(0L);
    }

    @Test
    public void IncomeRepository_GateAll_ReturnsMoreThenOneIncome() {

        User user = User.builder()
                .firstName("Test")
                .lastName("TestLast")
                .username("UserTest")
                .incomes(new ArrayList<>())
                .expenses(new ArrayList<>())
                .build();

        userRepository.save(user);
        IncomeCategory incomeCategory = incomeCategoryRepository.getReferenceById(1L);

        List<Income> incomeList = incomeRepository.findAll();
        int qtyBefore = incomeList.size();

        Income income1 = Income.builder()
                .user(user)
                .amount(BigDecimal.valueOf(77))
                .category(incomeCategory)
                .date(LocalDate.now())
                .build();
        Income income2 = Income.builder()
                .user(user)
                .amount(BigDecimal.valueOf(177))
                .category(incomeCategory)
                .date(LocalDate.now())
                .build();

        incomeRepository.save(income1);
        incomeRepository.save(income2);

        incomeList = incomeRepository.findAll();

        Assertions.assertThat(incomeList).isNotNull();
        Assertions.assertThat(incomeList.size()).isEqualTo(qtyBefore + 2);
    }

    @Test
    public void IncomeRepository_FindById_ReturnIncome() {

        User user = User.builder()
                .firstName("Test")
                .lastName("TestLast")
                .username("UserTest")
                .incomes(new ArrayList<>())
                .expenses(new ArrayList<>())
                .build();

        userRepository.save(user);
        IncomeCategory incomeCategory = incomeCategoryRepository.getReferenceById(1L);

        Income income = Income.builder()
                .user(user)
                .amount(BigDecimal.valueOf(77))
                .category(incomeCategory)
                .date(LocalDate.now())
                .build();

        incomeRepository.save(income);

        Optional<Income> incomeReturn = incomeRepository.findById(income.getId());
        Assertions.assertThat(incomeReturn.isPresent()).isTrue();
    }
}
