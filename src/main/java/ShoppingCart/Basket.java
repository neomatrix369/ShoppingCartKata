package ShoppingCart;

import static java.util.Collections.unmodifiableList;
import static ShoppingCart.CurrencyGBP.GBP;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Basket {
  private List<BasketItem> items;
  private final LocalDate creationDate;
  private final ProductRepository productRepository;
  private CurrencyGBP total = GBP(0.0);

  public Basket(
      List<BasketItem> items,
      LocalDate creationDate,
      ProductRepository productRepository) {
    this.items = items;
    this.creationDate = creationDate;
    this.productRepository = productRepository;
    updateTotal();
  }

  public Basket(LocalDate creationDate, ProductRepository productRepository) {
    this.items = createEmptyBasket();
    this.creationDate = creationDate;
    this.productRepository = productRepository;
    updateTotal();
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  private ArrayList<BasketItem> createEmptyBasket() {return new ArrayList<>();}

  public Basket addItem(UserID userId, ProductID productId, int quantity) {
    List<BasketItem> items = createEmptyBasket();
    items.addAll(unmodifiableList(this.items));
    items.add(new BasketItem(productId, quantity));
    return new Basket(items, creationDate, productRepository);
  }

  private void updateTotal() {
    items.forEach(
        basketItem -> {
          final Product product = productRepository.getProductBy(basketItem.getProductId());
          total = total.plus(basketItem.getTotalFor(product));
        }
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
