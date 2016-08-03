package ShoppingCart;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketService {
  private BasketsRepository basketsRepository;
  private Clock clock;

  public ShoppingBasketService(Clock clock, BasketsRepository basketsRepository) {
    this.clock = clock;
    this.basketsRepository = basketsRepository;
  }

  public Basket basketFor(UserID userId) {
    return basketsRepository.get(userId);
  }

  public void addItem(UserID userId, ProductID productId, int quantity) {
    Basket basket = getFilledOrEmptyBasket(userId);
    List<BasketItem> items = new ArrayList<>();
    items.addAll(getBasketItemsFrom(basket));
    items.add(new BasketItem(productId, quantity));
    basketsRepository.put(userId, new Basket(items, clock.getCurrentDate()));
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
      basket = new Basket(new ArrayList<>(), clock.getCurrentDate());
    }
    return basket;
  }
}
