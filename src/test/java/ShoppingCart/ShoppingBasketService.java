package ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketService {
  private BasketsRepository basketsRepository;
  private Clock clock;

  public ShoppingBasketService(Clock clock, BasketsRepository basketsRepository) {
    this.clock = clock;
    this.basketsRepository = basketsRepository;
  }

  public Basket basketFor(UserID userId) {
    return basketsRepository.get(userId);
  }

  public void addItem(UserID userId, ProductID productId, int quantity) {
    List<BasketItem> items = new ArrayList<>();
    items.add(new BasketItem(productId, quantity));
    basketsRepository.put(userId, new Basket(items, clock.getCurrentDate()));
  }
}
