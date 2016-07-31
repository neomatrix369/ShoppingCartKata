package ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class Baskets {
  private Map<UserID, Basket> items = new HashMap<>();

  public Basket getBasketFor(UserID userID) {
    return items.get(userID);
  }

  public void addItemFor(UserID userId, ProductID productId, int quantity) {
    Basket basket = getBasketOrCreateEmptyIfNotFoundFor(userId);
    basket.addItem(new Item(productId, quantity));
    items.put(userId, basket);
  }

  private Basket getBasketOrCreateEmptyIfNotFoundFor(UserID userId) {
    Basket basket = getBasketFor(userId);
    if (basket == null) {
      basket = new Basket();
    }
    return basket;
  }
}
