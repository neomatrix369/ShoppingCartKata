package ShoppingCart;

public class BasketItem {
  private final ProductID productID;
  private final int quantity;

  public BasketItem(ProductID productID, int quantity) {
    this.productID = productID;
    this.quantity = quantity;
  }
}
