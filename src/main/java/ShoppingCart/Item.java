package ShoppingCart;

public class Item {
  private final ProductID productId;
  private final int quantity;

  public Item(ProductID productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public ProductID getProductId() {
      return productId;
  }

  public int getQuantity() {
    return quantity;
  }
}
