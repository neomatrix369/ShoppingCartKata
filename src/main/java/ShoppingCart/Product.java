package ShoppingCart;

public class Product {
  private final ProductID productID;
  private final String title;
  private final Money price;

  public Product(ProductID productID, String title, Money price) {
    this.productID = productID;
    this.title = title;
    this.price = price;
  }
}
