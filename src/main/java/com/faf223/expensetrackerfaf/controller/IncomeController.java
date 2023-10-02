package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping()
    public ResponseEntity<List<Income>> getAllIncomes() {
        List<Income> incomes = incomeService.getIncomes();
        if (!incomes.isEmpty()) return ResponseEntity.ok(incomes);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<Income> createNewIncome(@ModelAttribute("income") Income income,
                                                    BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            incomeService.createOrUpdateIncome(income);
            return ResponseEntity.ok(income);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping()
    public ResponseEntity<Income> updateIncome(@ModelAttribute("income") Income income,
                                                 BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            incomeService.createOrUpdateIncome(income);
            return ResponseEntity.ok(income);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userUuid}")
    public ResponseEntity<List<Income>> getIncomesByUser(@PathVariable String userUuid) {
        List<Income> incomes = incomeService.getIncomesByUserId(userUuid);
        if (!incomes.isEmpty()) return ResponseEntity.ok(incomes);
        else return ResponseEntity.notFound().build();
    }
}

