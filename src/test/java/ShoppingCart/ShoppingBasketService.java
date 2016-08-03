package ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class ShoppingBasketService {
  private Map<UserID, Basket> baskets = new HashMap<>();

  public Basket basketFor(UserID userId) {
    return baskets.get(userId);
  }
}
