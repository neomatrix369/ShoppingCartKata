package ShoppingCart.service;

import org.junit.Before;
import org.junit.Test;

import ShoppingCart.domain.OutOfStockException;
import ShoppingCart.domain.ProductID;

public class StockServiceShould {

  private static final ProductID ANY_PRODUCT = new ProductID(100001);
  private StockService stockService;

  @Before
  public void initialise() {
    stockService = new StockService();
  }

  @Test (expected = OutOfStockException.class) public void
  throw_an_exception_when_deducting_quantity_for_an_item_that_does_not_have_any_stock() throws OutOfStockException {
    stockService.deductStockOrThrowError(ANY_PRODUCT, 1);
  }

  @Test (expected = OutOfStockException.class) public void
  throw_an_exception_when_deducting_quantity_for_an_item_with_not_enough_stock() throws OutOfStockException {
    stockService.updateStock(ANY_PRODUCT, 2);

    stockService.deductStockOrThrowError(ANY_PRODUCT, 3);
  }

  @Test public void
  throw_no_exception_when_deducting_quantity_for_an_item_with_enough_stock() throws OutOfStockException {
    stockService.updateStock(ANY_PRODUCT, 3);

    stockService.deductStockOrThrowError(ANY_PRODUCT, 2);
    stockService.deductStockOrThrowError(ANY_PRODUCT, 1);
  }
}
