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

    @Test
    public void IncomeRepository_FindByUser_ReturnIncomes() {

        Optional<User> user = userRepository.findByUsername("Balaban");
        Assertions.assertThat(user.isPresent()).isTrue();

        List<Income> incomes = incomeRepository.findByUser(user.get());

        Assertions.assertThat(incomes).isNotNull();
    }

    @Test
    public void IncomeRepository_FindByDate_ReturnIncomes() {

        List<Income> incomes = incomeRepository.findByDate(LocalDate.of(2023,10,7));

        Assertions.assertThat(incomes).isNotNull();
    }

    @Test
    public void IncomeRepository_UpdateIncome_ReturnIncomeNotNull() {

        Optional<User> user = userRepository.findByUsername("Deep Deep");
        Assertions.assertThat(user.isPresent()).isTrue();
        IncomeCategory incomeCategory = incomeCategoryRepository.getReferenceById(4L);

        Income income = Income.builder()
                .user(user.get())
                .amount(BigDecimal.valueOf(700))
                .category(incomeCategory)
                .date(LocalDate.of(2023,10,5))
                .build();

        incomeRepository.save(income);

        Optional<Income> incomeSave = incomeRepository.findById(income.getId());
        Assertions.assertThat(incomeSave).isNotNull();

        Income incomeToUpdate = incomeSave.get();
        incomeToUpdate.setDate(LocalDate.of(2023,10,6));
        incomeToUpdate.setAmount(BigDecimal.valueOf(777));

        Income updatedIncome = incomeRepository.save(incomeToUpdate);

        Assertions.assertThat(updatedIncome).isNotNull();
        Assertions.assertThat(updatedIncome.getAmount()).isEqualTo(BigDecimal.valueOf(777));
    }

    @Test
    public void IncomeRepository_DeleteIncome_ReturnIncomeNull() {

        Optional<User> user = userRepository.findByUsername("Deep Deep");
        Assertions.assertThat(user.isPresent()).isTrue();
        IncomeCategory incomeCategory = incomeCategoryRepository.getReferenceById(4L);

        Income income = Income.builder()
                .user(user.get())
                .amount(BigDecimal.valueOf(700))
                .category(incomeCategory)
                .date(LocalDate.of(2023,10,5))
                .build();

        incomeRepository.save(income);
        incomeRepository.deleteById(income.getId());

        Optional<Income> deletedIncome = incomeRepository.findById(income.getId());

        Assertions.assertThat(deletedIncome.isPresent()).isFalse();
    }

}
