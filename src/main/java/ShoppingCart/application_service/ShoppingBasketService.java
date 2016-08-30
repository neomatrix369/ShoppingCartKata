package ShoppingCart.application_service;

import ShoppingCart.domain.Basket;
import ShoppingCart.domain.BasketItem;
import ShoppingCart.domain.ProductID;
import ShoppingCart.domain.UserID;
import ShoppingCart.infrastructure.BasketRepository;
import ShoppingCart.infrastructure.Clock;
import ShoppingCart.infrastructure.ProductRepository;

public class ShoppingBasketService {
  private final BasketRepository basketRepository;
  private final ProductRepository productRepository;
  private final Clock clock;

  public ShoppingBasketService(
      Clock clock,
      BasketRepository basketRepository,
      ProductRepository productRepository) {
    this.clock = clock;
    this.basketRepository = basketRepository;
    this.productRepository = productRepository;
  }

  public Basket basketFor(UserID userId) {
    return basketRepository.getBasketFor(userId);
  }

  public void addItem(UserID userId, ProductID productId, int quantity) {
    Basket basket = basketFor(userId);
    if (basket == null) {
      basket = new Basket(clock.getCurrentDate(), productRepository);
    }

    final BasketItem basketItem = new BasketItem(productId, quantity);
    basketRepository.addBasketFor(userId, basket.addItem(basketItem));
  }
}
