package ShoppingCart;

import static java.lang.String.*;
import static java.time.LocalDate.now;

import static ShoppingCart.Amount.£;
import static ShoppingCart.ProductRepository.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Basket {
  private LocalDate creationDate;
  private Map<ProductID, Integer> items = new HashMap<>();
  private Amount total = £(0.0);

  public void setTotal(Amount total) {
    this.total = total;
  }

  public void addItem(BasketItem basketItem) {
    if (items.size() == 0) {
      creationDate = now();
    }

    items.put(basketItem.getProductId(), basketItem.getQuantity());

    updateBasketTotal(basketItem);
  }

  private void updateBasketTotal(BasketItem basketItem) {
    total = total.plus(
        basketItem.getTotalFor(
            getProductForId(basketItem.getProductId())
        )
    );
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
