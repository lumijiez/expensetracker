package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.dto.IncomeCreationDTO;
import com.faf223.expensetrackerfaf.dto.IncomeDTO;
import com.faf223.expensetrackerfaf.dto.mappers.IncomeMapper;
import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;
    private final IncomeMapper incomeMapper;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<IncomeDTO>> getAllIncomes() {
        List<IncomeDTO> incomes = incomeService.getIncomes().stream().map(incomeMapper::toDto).collect(Collectors.toList());
        if (!incomes.isEmpty()) return ResponseEntity.ok(incomes);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<IncomeDTO> createNewIncome(@RequestBody IncomeCreationDTO incomeDTO,
                                                     BindingResult bindingResult) {
        Income income = incomeMapper.toIncome(incomeDTO);
        if (!bindingResult.hasErrors()) {
            incomeService.createOrUpdateIncome(income);
            return ResponseEntity.ok(incomeMapper.toDto(income));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping()
    public ResponseEntity<IncomeDTO> updateIncome(@RequestBody IncomeCreationDTO incomeDTO,
                                                  BindingResult bindingResult) {
        Income income = incomeMapper.toIncome(incomeDTO);
        if (!bindingResult.hasErrors()) {
            incomeService.createOrUpdateIncome(income);
            return ResponseEntity.ok(incomeMapper.toDto(income));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userUuid}")
    public ResponseEntity<List<IncomeDTO>> getIncomesByUser(@PathVariable String userUuid) {
        List<IncomeDTO> incomes = incomeService.getIncomesByUserId(userUuid).stream().map(incomeMapper::toDto).collect(Collectors.toList());
        if (!incomes.isEmpty()) return ResponseEntity.ok(incomes);
        else return ResponseEntity.notFound().build();
    }
}

