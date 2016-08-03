package ShoppingCart;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Basket {
  private final List<BasketItem> items;
  private final LocalDate date;

  public Basket(List<BasketItem> items, LocalDate date) {
    this.items = items;
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Basket basket = (Basket) o;

    return new EqualsBuilder()
        .append(items, basket.items)
        .append(date, basket.date)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(items)
        .append(date)
        .toHashCode();
  }

  @Override
  public String toString() {
    return String.format("Basket{items=%s, date=%s}", items, date);
  }
}
