package foodordering.demo.implemantation;

public enum SelectionStrategyEnum {
    TOTAL_ORDER_PRICE,
    RESTAURANT_RATING;

    public static String[] getValues() {
        return new String[] { TOTAL_ORDER_PRICE.name(), RESTAURANT_RATING.name() };
    }
}
