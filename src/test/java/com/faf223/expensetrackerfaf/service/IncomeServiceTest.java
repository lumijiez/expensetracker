package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.model.IncomeCategory;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.IncomeCategoryRepository;
import com.faf223.expensetrackerfaf.repository.IncomeRepository;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository;
    @Mock
    private IncomeCategoryRepository incomeCategoryRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private IncomeService incomeService;

    @Test
    public void IncomeService_CreateIncome_ReturnsIncome() {

        // TODO: move income creation to service or move this test to controller tests
        Optional<User> user = userRepository.findByUsername("Deep Deep");
        Assertions.assertThat(user.isPresent()).isTrue();
        IncomeCategory incomeCategory = incomeCategoryRepository.getReferenceById(4L);

        Income income = Income.builder()
                .user(user.get())
                .amount(BigDecimal.valueOf(700))
                .category(incomeCategory)
                .date(LocalDate.of(2023,10,5))
                .build();



    }

}
