package ShoppingCart.service;

import static java.lang.String.format;

import java.time.LocalDate;

import ShoppingCart.infrastructure.Console;
import ShoppingCart.domain.Basket;
import ShoppingCart.domain.BasketItem;
import ShoppingCart.domain.OutOfStockException;
import ShoppingCart.domain.ProductID;
import ShoppingCart.domain.UserID;
import ShoppingCart.infrastructure.BasketsRepository;
import ShoppingCart.infrastructure.Clock;
import ShoppingCart.infrastructure.ProductRepository;

public class ShoppingBasketService {
  private final BasketsRepository basketsRepository;
  private final ProductRepository productRepository;
  private final Console console;
  private final Clock clock;
  private final StockService stockService;
  private final DiscountService discountService;

  public ShoppingBasketService(
      Console console,
      Clock clock,
      BasketsRepository basketsRepository,
      ProductRepository productRepository,
      StockService stockService,
      DiscountService discountService) {
    this.console = console;
    this.clock = clock;
    this.basketsRepository = basketsRepository;
    this.productRepository = productRepository;
    this.stockService = stockService;
    this.discountService = discountService;
  }

  public Basket basketFor(UserID userId) {
    return basketsRepository.getBasketFor(userId);
  }

  public void addItem(UserID userId, ProductID productId, int quantity) throws OutOfStockException {
    stockService.deductStockOrThrowError(productId, quantity);

    Basket basket = basketFor(userId);
    final LocalDate currentDate = clock.getCurrentDate();
    if (basket == null) {
      basket = new Basket(currentDate, productRepository, discountService);
      console.print(
          format("[BASKET CREATED]: Created[\"%s\"], User[%s]", currentDate, userId));
    }

    final BasketItem basketItem = new BasketItem(productId, quantity);
    basketsRepository.addBasketFor(userId, basket.addItem(basketItem));
    console.print(
        format("[ITEM ADDED TO SHOPPING CART]: Added[\"%s\"], User[%s], Product[%s], Quantity[%d], Price[%s]",
            currentDate, userId, productId, quantity, productRepository.getProductBy(productId).getPrice()));
  }
}
