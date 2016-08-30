package ShoppingCart.infrastructure;

import java.util.HashMap;
import java.util.Map;

import ShoppingCart.domain.ProductID;

public class StockRepository {

  private Map<ProductID, Integer> items = new HashMap<>();

  public int get(ProductID productId) {
    return items.getOrDefault(productId, 0);
  }

  public void put(ProductID productId, int quantity) {
    items.put(productId, quantity);
  }
}
