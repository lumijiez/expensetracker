package com.faf223.expensetrackerfaf.model;

import com.faf223.expensetrackerfaf.util.IMoneyTransaction;
import jakarta.persistence.*;

@Entity
@Table(name = "income")
public class Income implements IMoneyTransaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private int amount;
    private Categories.IncomeCategory category;

    public Income(User user, int amount, Categories.IncomeCategory category) {
        this.user = user;
        this.amount = amount;
        this.category = category;
    }

    public Income() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public String getCategory() {
        return category.getEffectiveName();
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCategory(Categories.IncomeCategory category) {
        this.category = category;
    }
}
