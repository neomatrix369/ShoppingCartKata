package ShoppingCart;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CurrencyGBP {
  private final double value;

  public CurrencyGBP(double value) {
    this.value = value;
  }

  public static CurrencyGBP GBP(double value) {
    return new CurrencyGBP(value);
  }

  public CurrencyGBP multiplyBy(int anotherValue) {
    return GBP(value * anotherValue);
  }

  public CurrencyGBP plus(CurrencyGBP anotherValue) {
    return GBP(value + anotherValue.value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    CurrencyGBP currencyGBP = (CurrencyGBP) o;

    return new EqualsBuilder()
        .append(value, currencyGBP.value)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(value)
        .toHashCode();
  }
}
