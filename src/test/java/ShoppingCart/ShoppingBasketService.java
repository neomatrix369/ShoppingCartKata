package ShoppingCart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingBasketService {
  private Map<UserID, Basket> baskets = new HashMap<>();
  private Clock clock;

  public ShoppingBasketService(Clock clock) {this.clock = clock;}

  public Basket basketFor(UserID userId) {
    return baskets.get(userId);
  }

  public void addItem(UserID userId, ProductID productId, int quantity) {
    List<BasketItem> items = new ArrayList<>();
    items.add(new BasketItem(productId, quantity));
    baskets.put(userId, new Basket(items, clock.getCurrentDate()));
  }
}
