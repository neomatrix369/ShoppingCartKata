package ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class BasketsRepository {
  private final Map<UserID, Basket> baskets = new HashMap<>();

  public Basket getBasketFor(UserID userId) {
    return baskets.get(userId);
  }

  public void addBasketFor(UserID userId, Basket basket) {
    baskets.put(userId, basket);
  }
}
