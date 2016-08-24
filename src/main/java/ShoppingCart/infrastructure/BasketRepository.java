package ShoppingCart.infrastructure;

import java.util.HashMap;
import java.util.Map;

import ShoppingCart.domain.Basket;
import ShoppingCart.domain.UserID;

public class BasketRepository {
  private final Map<UserID, Basket> baskets = new HashMap<>();

  public Basket getBasketFor(UserID userId) {
    return baskets.get(userId);
  }

  public void addBasketFor(UserID userId, Basket basket) {
    baskets.put(userId, basket);
  }
}
