package ShoppingCart;

import static ShoppingCart.Money.GBP;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Basket {
  private final List<BasketItem> items;
  private final LocalDate date;
  private final ProductRepository productRepository;
  private Money total = GBP(0.0);

  public Basket(
      List<BasketItem> items,
      LocalDate date,
      ProductRepository productRepository) {
    this.items = items;
    this.date = date;
    this.productRepository = productRepository;
    updateTotal();
  }

  private void updateTotal() {
    items.forEach(
        basketItem -> total = total.plus(productRepository.getTotalFor(basketItem))
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Basket basket = (Basket) o;

    return new EqualsBuilder()
        .append(total, basket.total)
        .append(items, basket.items)
        .append(date, basket.date)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(items)
        .append(date)
        .append(total)
        .toHashCode();
  }

  public List<BasketItem> getItems() {
    return items;
  }
}
