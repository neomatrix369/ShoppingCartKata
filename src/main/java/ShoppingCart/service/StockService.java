package ShoppingCart.service;

import java.util.HashMap;
import java.util.Map;

import ShoppingCart.domain.ProductID;

public class StockService {

  private Map<ProductID, Integer> items = new HashMap<>();

  public int available(ProductID productId, int quantity) {
    return items.getOrDefault(productId, 0);
  }

  public void addStock(ProductID productId, int quantity) {
    items.put(productId, quantity);
  }
}
