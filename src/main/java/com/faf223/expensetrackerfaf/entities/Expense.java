package com.faf223.expensetrackerfaf.entities;

import com.faf223.expensetrackerfaf.util.IMoneyTransaction;
import jakarta.persistence.*;

@Entity
@Table(name = "expense")
public class Expense implements IMoneyTransaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private int amount;
    private Categories.ExpenseCategory category;

    public Expense(User user, int amount, Categories.ExpenseCategory category) {
        this.user = user;
        this.amount = amount;
        this.category = category;
    }

    public Expense() {}

    @Override
    public User getUser() {
        return user;
    }

    public void setUserId(int userId) {
        this.user = user;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String getCategory() {
        return category.getEffectiveName();
    }

    public void setCategory(Categories.ExpenseCategory category) {
        this.category = category;
    }
}
