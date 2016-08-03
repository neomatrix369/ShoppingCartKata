package ShoppingCart;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
  private static Map<ProductID, Product> products = new HashMap<>();

  public static Product getProductBy(ProductID productId) {
    return products.get(productId);
  }
}
