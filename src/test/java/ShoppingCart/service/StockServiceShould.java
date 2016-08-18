package ShoppingCart.service;

import org.junit.Test;

import ShoppingCart.domain.OutOfStockException;
import ShoppingCart.domain.ProductID;

public class StockServiceShould {
  @Test (expected = OutOfStockException.class) public void
  throws_an_exception_when_item_does_not_have_any_stock() throws OutOfStockException {
    ProductID productId = new ProductID(100001);
    StockService stockService = new StockService();

    stockService.deductStockOrThrowError(productId, 1);
  } 
}
