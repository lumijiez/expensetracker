package entities;

public class Categories {

    public enum ExpenseCategory {
        CREDIT, BUY, BUSINESS, ENTERTAINMENT, RESTAURANTS_AND_CAFE, COMMUNAL_PAYMENTS, SUPERMARKET, MISC;

        public String getEffectiveName() {
            return Categories.getEffectiveName(this.name());
        }
    }

    public enum IncomeCategory {
        P2P, SALARY, GIFT, CREDIT, MISC;

        public String getEffectiveName() {
            return Categories.getEffectiveName(this.name());
        }
    }

    private static String getEffectiveName(String name) {
        String[] arr = name.split("_");
        StringBuilder result = new StringBuilder();
        for(String entry : arr) {
            String[] entryArr = entry.split("");
            StringBuilder builder = new StringBuilder(entryArr[0]);
            for(int i = 1; i < entry.length(); i++) builder.append(entryArr[i].toLowerCase());
            result.append(builder).append(" ");
        }
        return result.toString();
    }

}
