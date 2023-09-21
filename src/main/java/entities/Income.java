package entities;

public class Income extends MoneyTransaction {

    private long userId;
    private int amount;
    private IncomeCategory category;

    public Income(long userId, int amount, IncomeCategory category) {
        this.userId = userId;
        this.amount = amount;
        this.category = category;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public String getCategory() {
        return category.toString();
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCategory(IncomeCategory category) {
        this.category = category;
    }
}
