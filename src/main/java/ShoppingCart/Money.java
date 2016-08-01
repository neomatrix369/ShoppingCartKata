package ShoppingCart;

import static ShoppingCart.Currency.GBP;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Money {

  private double value;
  private Currency currency;

  public Money(double value, Currency currency) {
    this.value = value;
    this.currency = currency;
  }

  public double getValue() {
    return value;
  }

  public static Money £(double value) {
    return new Money(value, GBP);
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

  public Money plus(Money totalForItem) {
    return totalForItem.plus(value);
  }

  private Money plus(double value) {
    return £(this.value + value);
  }
}
