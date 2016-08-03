package ShoppingCart;

public class Money {
  private final double value;
  private final Currency currency;

  public Money(double value, Currency currency) {
    this.value = value;
    this.currency = currency;
  }

  public static Money GBP(double value) {
    return new Money(value, Currency.GBP);
  }
}
