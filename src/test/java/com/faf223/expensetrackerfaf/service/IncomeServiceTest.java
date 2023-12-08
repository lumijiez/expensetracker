package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.IMoneyTransaction;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

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

//    public void createOrUpdate(IMoneyTransaction income) {
//        incomeRepository.save((Income) income);
//    }

    @Test
    public void IncomeService_CreateIncome_ReturnsIncome() {

        User user = User.builder().build();
        when(userRepository.findByUsername("Deep Deep")).thenReturn(Optional.of(user));
        user = userRepository.findByUsername("Deep Deep").get();

        IncomeCategory incomeCategory = IncomeCategory.builder().build();
        when(incomeCategoryRepository.findById(1L)).thenReturn(Optional.of(incomeCategory));
        incomeCategory = incomeCategoryRepository.findById(1L).get();

        Income income = Income.builder()
                .user(user)
                .amount(BigDecimal.valueOf(111))
                .category(incomeCategory)
                .date(LocalDate.of(2023,10,5))
                .build();

        when(incomeRepository.save(Mockito.any(Income.class))).thenReturn(income);
        incomeService.createOrUpdate(income);

        Assertions.assertThat(income.getId()).isGreaterThan(0);

        List<Income> incomes = new ArrayList<>();
        when(incomeRepository.findByUser(user)).thenReturn(List.of(income));
        incomes = incomeRepository.findByUser(user);

        // TODO: finish service test

    }

}
