package ShoppingCart;

import static ShoppingCart.Money.GBP;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
  public static final ProductID PRODUCT_ID_100001 = new ProductID(100001);

  private static Map<ProductID, Product> products = new HashMap<ProductID, Product>()
  {
    {
      put(PRODUCT_ID_100001,
         new Product(PRODUCT_ID_100001, "Lord of the Rings", GBP(10.00)));
    }
  };

  public static Product getProductBy(ProductID productId) {
    return products.get(productId);
  }
}
