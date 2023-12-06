package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.repository.IncomeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private IncomeService incomeService;

    @Test
    public void IncomeService_CreateIncome_ReturnsIncome() {



    }

}
