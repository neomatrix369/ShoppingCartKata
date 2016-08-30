package ShoppingCart.application_service;

import static java.lang.String.format;

import java.time.LocalDate;

import ShoppingCart.Console;
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
  private final Console console;
  private final Clock clock;

  public ShoppingBasketService(
      Console console,
      Clock clock,
      BasketRepository basketRepository,
      ProductRepository productRepository) {
    this.console = console;
    this.clock = clock;
    this.basketRepository = basketRepository;
    this.productRepository = productRepository;
  }

  public Basket basketFor(UserID userId) {
    return basketRepository.getBasketFor(userId);
  }

  public void addItem(UserID userId, ProductID productId, int quantity) {
    Basket basket = basketFor(userId);
    final LocalDate currentDate = clock.getCurrentDate();
    if (basket == null) {
      basket = new Basket(currentDate, productRepository);
      console.print(
          format("[BASKET CREATED]: Created[\"%s\"], User[%s]", currentDate, userId));
    }

    final BasketItem basketItem = new BasketItem(productId, quantity);
    basketRepository.addBasketFor(userId, basket.addItem(basketItem));
    console.print(
        format("[ITEM ADDED TO SHOPPING CART]: Added[\"%s\"], User[%s], Product[%s], Quantity[%d], Price[%s]",
            currentDate, userId, productId, quantity, productRepository.getProductBy(productId).getPrice()));

  }
}
