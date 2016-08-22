package ShoppingCart.domain;

import static ShoppingCart.domain.Category.BOOK;
import static ShoppingCart.domain.Category.DVD;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import ShoppingCart.infrastructure.ProductRepository;

public class Basket {
  private static final double TEN_PERCENT = 10.00;
  private static final double TWENTY_PERCENT = 20.00;

  private List<BasketItem> items = new ArrayList<>();
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
    return new Basket(newItems, creationDate, productRepository);
  }

  public GBP getTotal() {
    if (total == null) {
      total = new GBP(0.0);
      items.forEach(this::calculateTotalFor);

      applyTenPercentDiscountForMoreThan3Books();
      applyTwentyPercentDiscountOneBookAndOneDVD();
    }

    return total;
  }

  private void applyTenPercentDiscountForMoreThan3Books() {
    long booksCount = items.stream()
        .filter(item -> getProductFor(item).isA(BOOK))
        .mapToInt(BasketItem::getQuantity)
        .sum();

    if (booksCount > 3) {
      total = total.reduceBy(TEN_PERCENT);
    }
  }

  private void applyTwentyPercentDiscountOneBookAndOneDVD() {
    long booksCount = items.stream()
        .filter(item -> getProductFor(item).isA(BOOK))
        .mapToInt(BasketItem::getQuantity)
        .sum();

    long dvdsCount = items.stream()
        .filter(item -> getProductFor(item).isA(DVD))
        .mapToInt(BasketItem::getQuantity)
        .sum();

    if ((booksCount >= 1) && (dvdsCount >= 1)) {
      total = total.reduceBy(TWENTY_PERCENT);
    }
  }

  private Product getProductFor(BasketItem item) {return productRepository.getProductBy(item.getProductId());}

  private void calculateTotalFor(BasketItem basketItem) {
    final Product product = getProductFor(basketItem);
    total = total.plus(product.getPrice().multiplyBy(basketItem.getQuantity()));
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
