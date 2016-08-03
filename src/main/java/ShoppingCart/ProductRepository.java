package ShoppingCart;

import static ShoppingCart.Category.BOOK;
import static ShoppingCart.Money.GBP;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
  public static final ProductID PRODUCT_ID_100001 = new ProductID(100001);
  static final ProductID PRODUCT_ID_100002 = new ProductID(100002);

  private static Map<ProductID, Product> products = new HashMap<ProductID, Product>()
  {
    {
      put(PRODUCT_ID_100001,
         new Product(PRODUCT_ID_100001, BOOK, "Lord of the Rings", GBP(10.00)));
      put(PRODUCT_ID_100002,
          new Product(PRODUCT_ID_100002, BOOK, "The Hobbit", GBP(5.00)));
    }
  };

  public static Product getProductBy(ProductID productId) {
    return products.get(productId);
  }
}
