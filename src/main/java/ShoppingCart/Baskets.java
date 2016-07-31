package ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class Baskets {
  private Map<UserID, Basket> items = new HashMap<>();

  public Basket getBasketFor(UserID userID) {
    return items.get(userID);
  }

  public void addItemFor(UserID userId, ProductID productId, int quantity) {
    items.put(userId, new Basket());
  }
}
