package ShoppingCart.service;

import ShoppingCart.domain.ProductID;

public class StockService {
  public int available(ProductID productId, int quantity) {
    if (productId.equals(new ProductID(200110)) && quantity == 2) {
      return 0;
    }
    return quantity;
  }
}
