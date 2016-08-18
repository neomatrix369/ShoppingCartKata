package ShoppingCart.service;

import ShoppingCart.domain.ProductID;

public class StockService {
  public int available(ProductID productId, int quantity) {
    return quantity;
  }
}
