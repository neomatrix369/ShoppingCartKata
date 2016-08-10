package ShoppingCart;

public class ShoppingBasketService {
  private final BasketsRepository basketsRepository;
  private final ProductRepository productRepository;
  private final Clock clock;

  public ShoppingBasketService(
      Clock clock,
      BasketsRepository basketsRepository,
      ProductRepository productRepository) {
    this.clock = clock;
    this.basketsRepository = basketsRepository;
    this.productRepository = productRepository;
  }

  public Basket basketFor(UserID userId) {
    return basketsRepository.getBasketFor(userId);
  }

  public void addItem(UserID userId, ProductID productId, int quantity) {
    Basket basket = basketFor(userId);
    if (basket == null) {
      basket = new Basket(clock.getCurrentDate(), productRepository);
    }
    basketsRepository.addBasketFor(userId, basket.addItem(productId, quantity));
  }
}
