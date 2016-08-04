package ShoppingCart;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Money {
  private final double value;
  private final Currency currency;

  public Money(double value, Currency currency) {
    this.value = value;
    this.currency = currency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Money money = (Money) o;

    return new EqualsBuilder()
        .append(value, money.value)
        .append(currency, money.currency)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(value)
        .append(currency)
        .toHashCode();
  }

  public static Money GBP(double value) {
    return new Money(value, Currency.GBP);
  }

  public double multiplyBy(int anotherValue) {
    return value * anotherValue;
  }

  public Money plus(Money anotherValue) {
    return GBP(value + anotherValue.value);
  }
}
