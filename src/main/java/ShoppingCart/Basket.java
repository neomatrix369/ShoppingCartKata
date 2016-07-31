package ShoppingCart;

import static java.lang.String.*;
import static java.time.LocalDate.now;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Basket {
  private LocalDate creationDate;
  private Map<ProductID, Integer> items = new HashMap<>();
  private double total = 0.0;

  public void setTotal(double total) {
    this.total = total;
  }

  public void addItem(Item item) {
    if (items.size() == 0) {
      creationDate = now();
    }
    items.put(item.getProductId(), item.getQuantity());

    updateTotal(item);
  }

  private void updateTotal(Item item) {
    Product product = ProductRepository.getProductForId(item.getProductId());
    total = total + product.getPrice() * item.getQuantity();
  }

  public void setCreationDate(LocalDate date) {
    this.creationDate = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Basket basket = (Basket) o;

    return new EqualsBuilder()
        .append(total, basket.total)
        .append(creationDate, basket.creationDate)
        .append(items, basket.items)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(creationDate)
        .append(items)
        .append(total)
        .toHashCode();
  }

  @Override
  public String toString() {
    return format("Basket{creationDate=%s, items=%s, total=%s}", creationDate, items, total);
  }
}
