package ShoppingCart.service;

import java.util.HashMap;
import java.util.Map;

import ShoppingCart.domain.OutOfStockException;
import ShoppingCart.domain.ProductID;

public class StockService {

  // TODO - might be better to put this in a StockRepository
  private Map<ProductID, Integer> items = new HashMap<>();

  public void reserveStock(ProductID productId, int quantity)
      throws OutOfStockException {
    if (available(productId) < quantity) {
      throw new OutOfStockException();
    }

    updateStock(productId, available(productId) - quantity);
  }

  private int available(ProductID productId) {
    return items.getOrDefault(productId, 0);
  }

  // TODO not required, when mocking reservedStock - it is a test helper
  public void updateStock(ProductID productId, int quantity) {
    items.put(productId, quantity);
  }
}
