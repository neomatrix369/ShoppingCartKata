package ShoppingCart;

public class BasketItem {
  private final ProductID productId;
  private final int quantity;

  public BasketItem(ProductID productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public ProductID getProductId() {
      return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public double getTotalFor(Product product) {
    return product.getTotalPriceFor(quantity);
  }
}
