package ShoppingCart.service;

import java.util.HashMap;
import java.util.Map;

import ShoppingCart.domain.ProductID;

public class StockService {

  private Map<ProductID, Integer> items = new HashMap<>();

  public int available(ProductID productId) {
    return items.getOrDefault(productId, 0);
  }

  public void addStock(ProductID productId, int quantity) {
    items.put(productId, quantity);
  }

  public void deductStock(ProductID productId, int quantity) {
    int availableQuantity = available(productId);
    int remainingQuantity = availableQuantity - quantity;
    items.put(productId, remainingQuantity);
  }
}
