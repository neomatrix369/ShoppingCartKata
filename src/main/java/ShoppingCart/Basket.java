package ShoppingCart;

import static java.util.Collections.unmodifiableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Basket {
  private List<BasketItem> items;
  private final LocalDate creationDate;
  private final ProductRepository productRepository;
  private GBP total;

  public Basket(
      List<BasketItem> items,
      LocalDate creationDate,
      ProductRepository productRepository) {
    this.items = items;
    this.creationDate = creationDate;
    this.productRepository = productRepository;
  }

  public Basket(LocalDate creationDate, ProductRepository productRepository) {
    this.items = emptyItemsList();
    this.creationDate = creationDate;
    this.productRepository = productRepository;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public GBP getTotal() {
    if (total == null) {
      total = new GBP(0.0);
    }

    items.forEach(
        basketItem -> {
          final Product product = productRepository.getProductBy(basketItem.getProductId());
          total = total.plus(basketItem.getTotalFor(product));
        }
    );
    return total;
  }

  private ArrayList<BasketItem> emptyItemsList() {return new ArrayList<>();}

  public Basket addItem(BasketItem basketItem) {
    List<BasketItem> items = emptyItemsList();
    items.addAll(unmodifiableList(this.items));
    items.add(basketItem);
    return new Basket(items, creationDate, productRepository);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Basket basket = (Basket) o;

    return new EqualsBuilder()
        .append(total, basket.total)
        .append(items, basket.items)
        .append(creationDate, basket.creationDate)
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
}
