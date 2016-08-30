package ShoppingCart.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ShoppingCart.domain.OutOfStockException;
import ShoppingCart.domain.ProductID;
import ShoppingCart.infrastructure.StockRepository;

public class StockServiceShould {

  private static final ProductID ANY_PRODUCT = new ProductID(100001);
  private StockRepository stockRepository;
  private StockService stockService;

  @Before
  public void initialise() {
    stockRepository = mock(StockRepository.class);
    stockService = new StockService(stockRepository);
  }

  @Test (expected = OutOfStockException.class) public void
  throw_an_exception_when_deducting_quantity_for_an_item_that_does_not_have_any_stock() throws OutOfStockException {
    when(stockRepository.get(ANY_PRODUCT)).thenReturn(0);

    stockService.reserveStock(ANY_PRODUCT, 1);
  }

  @Test (expected = OutOfStockException.class) public void
  throw_an_exception_when_deducting_quantity_for_an_item_with_not_enough_stock() throws OutOfStockException {
    when(stockRepository.get(ANY_PRODUCT)).thenReturn(2);

    stockService.reserveStock(ANY_PRODUCT, 3);
  }

  @Test public void
  throw_no_exception_when_deducting_quantity_for_an_item_with_enough_stock() throws OutOfStockException {
    when(stockRepository.get(ANY_PRODUCT)).thenReturn(3, 1);

    stockService.reserveStock(ANY_PRODUCT, 2);
    stockService.reserveStock(ANY_PRODUCT, 1);
  }
}
