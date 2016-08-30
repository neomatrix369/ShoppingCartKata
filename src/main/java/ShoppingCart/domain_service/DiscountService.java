package ShoppingCart.domain_service;

import static ShoppingCart.domain.Category.BOOK;
import static ShoppingCart.domain.Category.DVD;

import java.util.List;

import ShoppingCart.domain.BasketItem;
import ShoppingCart.domain.Category;
import ShoppingCart.domain.Product;
import ShoppingCart.infrastructure.ProductRepository;

public class DiscountService {
  private static final double TEN_PERCENT = 10.00;
  private static final double TWENTY_PERCENT = 20.00;

  private final ProductRepository productRepository;

  public DiscountService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public double applyTenPercentDiscountForMoreThanThreeBooks(List<BasketItem> items) {
    if (countForProductCategory(items, BOOK) > 3) {
      return TEN_PERCENT;
    }
    return 0.0;
  }

  public double applyTwentyPercentDiscountOneBookAndOneDVD(List<BasketItem> items) {
    if ((countForProductCategory(items, BOOK) >= 1) &&
        (countForProductCategory(items, DVD) >= 1)) {
      return TWENTY_PERCENT;
    }
    return 0.0;
  }

  private long countForProductCategory(List<BasketItem> items, Category category) {
    return items.stream()
        .filter(item -> getProductFor(item).isA(category))
        .mapToInt(BasketItem::getQuantity)
        .sum();
  }

  private Product getProductFor(BasketItem item) {return productRepository.getProductBy(item.getProductId());}

  public double getDiscountFor(List<BasketItem> items) {
    double discount = applyTwentyPercentDiscountOneBookAndOneDVD(items);
    if (discount == 0.0) {
      discount = applyTenPercentDiscountForMoreThanThreeBooks(items);
    }
    return discount;
  }
}
