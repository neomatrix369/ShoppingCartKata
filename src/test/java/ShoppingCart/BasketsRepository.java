package ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class BasketsRepository {
  private Map<UserID, Basket> baskets = new HashMap<>();

  public Basket get(UserID userId) {
    return baskets.get(userId);
  }

  public void put(UserID userId, Basket basket) {
    baskets.put(userId, basket);
  }
}
