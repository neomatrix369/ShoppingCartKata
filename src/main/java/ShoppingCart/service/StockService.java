package ShoppingCart.service;

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

    updateStock(productId, available(productId) - quantity);
  }

  private int available(ProductID productId) {
    return stockRepository.get(productId);
  }

  protected void updateStock(ProductID productId, int quantity) {
    stockRepository.put(productId, quantity);
  }
}
