package ShoppingCart;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

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
    Basket basket = getFilledOrEmptyBasket(userId);
    List<BasketItem> items = new ArrayList<>();
    items.addAll(getBasketItemsFrom(basket));
    items.add(new BasketItem(productId, quantity));
    basketsRepository.addBasketFor(userId, new Basket(items, clock.getCurrentDate(), productRepository));
  }

  private List<BasketItem> getBasketItemsFrom(Basket basket) {
    List<BasketItem> items = basket.getItems();
    if (items == null) {
      return new ArrayList<>();
    }
    return unmodifiableList(items);
  }

  private Basket getFilledOrEmptyBasket(UserID userId) {
    Basket basket = basketFor(userId);
    if (basket == null) {
      basket = new Basket(new ArrayList<>(), clock.getCurrentDate(), productRepository);
    }
    return basket;
  }
}
