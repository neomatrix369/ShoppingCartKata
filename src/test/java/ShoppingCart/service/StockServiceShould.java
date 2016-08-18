package ShoppingCart.service;

import org.junit.Test;

import ShoppingCart.domain.OutOfStockException;
import ShoppingCart.domain.ProductID;

public class StockServiceShould {
  @Test (expected = OutOfStockException.class) public void
  throw_an_exception_when_deducting_quantity_for_an_item_that_does_not_have_any_stock() throws OutOfStockException {
    ProductID productId = new ProductID(100001);
    StockService stockService = new StockService();

    stockService.deductStockOrThrowError(productId, 1);
  }

  @Test (expected = OutOfStockException.class) public void
  throw_an_exception_when_deducting_quantity_for_an_item_with_not_enough_stock() throws OutOfStockException {
    ProductID productId = new ProductID(100001);
    StockService stockService = new StockService();
    stockService.updateStock(productId, 2);

    stockService.deductStockOrThrowError(productId, 3);
  }

  @Test public void
  throw_no_exception_when_deducting_quantity_for_an_item_with_enough_stock() throws OutOfStockException {
    ProductID productId = new ProductID(100001);
    StockService stockService = new StockService();
    stockService.updateStock(productId, 3);

    stockService.deductStockOrThrowError(productId, 2);
    stockService.deductStockOrThrowError(productId, 1);
  }
}
