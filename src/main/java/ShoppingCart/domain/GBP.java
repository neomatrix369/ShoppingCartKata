package ShoppingCart.domain;

import static java.lang.String.format;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class GBP {
  private final double value;

  public GBP(double value) {
    this.value = value;
  }

  public GBP multiplyBy(int anotherValue) {
    return new GBP(value * anotherValue);
  }

  public GBP plus(GBP anotherValue) {
    return new GBP(value + anotherValue.value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    GBP that = (GBP) o;

    return new EqualsBuilder()
        .append(value, that.value)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(value)
        .toHashCode();
  }

  @Override
  public String toString() {
    return format("£%.2f", value);
  }
}
