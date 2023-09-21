package entities;

public class Expense implements IMoneyTransaction {

    private int userId;
    private int amount;
    private Categories.ExpenseCategory category;

    public Expense(int userId, int amount, Categories.ExpenseCategory category) {
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
        return category.getEffectiveName();
    }

    public void setCategory(Categories.ExpenseCategory category) {
        this.category = category;
    }
}
