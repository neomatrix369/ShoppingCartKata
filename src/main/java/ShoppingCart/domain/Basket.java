package ShoppingCart.domain;

import static java.lang.String.*;
import static java.util.Arrays.asList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import ShoppingCart.infrastructure.ProductRepository;

public class Basket {
  private List<BasketItem> items = new ArrayList<>();
  private final LocalDate creationDate;
  private final ProductRepository productRepository;
  private GBP total;

  public Basket(
      LocalDate creationDate,
      ProductRepository productRepository,
      BasketItem... items) {
    this.creationDate = creationDate;
    this.productRepository = productRepository;
    this.items = asList(items);
  }

  public Basket(LocalDate creationDate, ProductRepository productRepository) {
    this.creationDate = creationDate;
    this.productRepository = productRepository;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public Basket addItem(BasketItem basketItem) {
    List<BasketItem> newItems = new ArrayList<>();
    newItems.addAll(this.items);
    newItems.add(basketItem);
    return new Basket(creationDate, productRepository, newItems.toArray(new BasketItem[newItems.size()]));
  }

  public GBP getTotal() {
    if (total == null) {
      total = new GBP(0.0);
      items.forEach(this::calculateTotalFor);
    }

    return total;
  }

  private void calculateTotalFor(BasketItem basketItem) {
    final Product product = productRepository.getProductBy(basketItem.getProductId());
    total = total.plus(product.getPrice().multiplyBy(basketItem.getQuantity()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Basket basket = (Basket) o;

    return new EqualsBuilder()
        .append(items, basket.items)
        .append(creationDate, basket.creationDate)
        .append(total, basket.total)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(items)
        .append(creationDate)
        .append(total)
        .toHashCode();
  }

  @Override
  public String toString() {
    return format("Basket{items=%s, creationDate=%s, total=%s}", items, creationDate, total);
  }
}
