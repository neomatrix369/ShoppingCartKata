package ShoppingCart;

import static ShoppingCart.Currency.GBP;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Amount {

  private double value;
  private Currency currency;

  public Amount(double value, Currency currency) {
    this.value = value;
    this.currency = currency;
  }

  public double getValue() {
    return value;
  }

  public static Amount £(double value) {
    return new Amount(value, GBP);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Amount amount = (Amount) o;

    return new EqualsBuilder()
        .append(value, amount.value)
        .append(currency, amount.currency)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(value)
        .append(currency)
        .toHashCode();
  }

  public Amount plus(Amount totalForItem) {
    return totalForItem.plus(value);
  }

  private Amount plus(double value) {
    return £(this.value + value);
  }
}
