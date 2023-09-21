package entities;

public class Income implements IMoneyTransaction {

    private long userId;
    private int amount;
    private Categories.IncomeCategory category;

    public Income(long userId, int amount, Categories.IncomeCategory category) {
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
        return category.getEffectiveName();
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCategory(Categories.IncomeCategory category) {
        this.category = category;
    }
}
