package ShoppingCart;

public class ShoppingBasketService {
  private Baskets baskets = new Baskets();

  public void addItem(UserID userId, ProductID productId, int quantity) {
    baskets.addItemFor(userId, productId, quantity);
  }

  public Basket basketFor(UserID userID) {
    return baskets.getBasketFor(userID);
  }
}
