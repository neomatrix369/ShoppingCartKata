package ShoppingCart.service;

import ShoppingCart.domain.Basket;
import ShoppingCart.domain.BasketItem;
import ShoppingCart.domain.ProductID;
import ShoppingCart.domain.UserID;
import ShoppingCart.infrastructure.BasketsRepository;
import ShoppingCart.infrastructure.Clock;
import ShoppingCart.infrastructure.ProductRepository;

public class ShoppingBasketService {
  private final BasketsRepository basketsRepository;
  private final ProductRepository productRepository;
  private final Clock clock;

  public ShoppingBasketService(
      Clock clock,
      BasketsRepository basketsRepository,
      ProductRepository productRepository) {
    this.clock = clock;
    this.basketsRepository = basketsRepository;
    this.productRepository = productRepository;
  }

  public Basket basketFor(UserID userId) {
    return basketsRepository.getBasketFor(userId);
  }

  public void addItem(UserID userId, ProductID productId, int quantity) {
    Basket basket = basketFor(userId);
    if (basket == null) {
      basket = new Basket(clock.getCurrentDate(), productRepository);
    }

    final BasketItem basketItem = new BasketItem(productId, quantity);
    basketsRepository.addBasketFor(userId, basket.addItem(basketItem));
  }
}
