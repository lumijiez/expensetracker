package entities;

public class Expense extends MoneyTransaction {

    private int userId;
    private int amount;
    private ExpensesCategory category;

    public Expense(int userId, int amount, ExpensesCategory category) {
        this.userId = userId;
        this.amount = amount;
        this.category = category;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        return category.toString();
    }

    public void setCategory(ExpensesCategory category) {
        this.category = category;
    }
}
