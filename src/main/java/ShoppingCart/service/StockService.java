package ShoppingCart.service;

import java.util.HashMap;
import java.util.Map;

import ShoppingCart.domain.OutOfStockException;
import ShoppingCart.domain.ProductID;

public class StockService {

  private Map<ProductID, Integer> items = new HashMap<>();

  public void deductStockOrThrowError(ProductID productId, int quantity)
      throws OutOfStockException {
    if (available(productId) < quantity) {
      throw new OutOfStockException();
    }

    deductStock(productId, quantity);
  }

  private int available(ProductID productId) {
    return items.getOrDefault(productId, 0);
  }

  private void deductStock(ProductID productId, int quantity) {
    updateStock(productId, available(productId) - quantity);
  }

  public void updateStock(ProductID productId, int quantity) {
    items.put(productId, quantity);
  }
}
