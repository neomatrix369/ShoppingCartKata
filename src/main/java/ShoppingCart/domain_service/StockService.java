package ShoppingCart.domain_service;

import ShoppingCart.domain.OutOfStockException;
import ShoppingCart.domain.ProductID;
import ShoppingCart.infrastructure.StockRepository;

public class StockService {

  private StockRepository stockRepository;

  public StockService(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  public void reserveStock(ProductID productId, int quantity)
      throws OutOfStockException {
    if (available(productId) < quantity) {
      throw new OutOfStockException();
    }

    stockRepository.put(productId, available(productId) - quantity);
  }

  private int available(ProductID productId) {
    return stockRepository.get(productId);
  }
}
