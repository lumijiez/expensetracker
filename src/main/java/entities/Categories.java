package entities;

public class Categories {

    public enum ExpenseCategory {
        MISC;

        public String getEffectiveName() {
            return Categories.getEffectiveName(this.name());
        }
    }

    public enum IncomeCategory {
        MISC;

        public String getEffectiveName() {
            return Categories.getEffectiveName(this.name());
        }
    }

    private static String getEffectiveName(String name) {
        String[] arr = name.split("");
        StringBuilder result = new StringBuilder(arr[0].toLowerCase());
        for(int i = 1; i < name.length(); i++) result.append(arr[i]);
        return result.toString();
    }

}
