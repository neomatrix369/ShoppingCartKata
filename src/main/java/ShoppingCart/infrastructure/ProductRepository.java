package ShoppingCart.infrastructure;

import static ShoppingCart.domain.Category.BOOK;
import static ShoppingCart.domain.Category.DVD;

import java.util.HashMap;
import java.util.Map;

import ShoppingCart.domain.Product;
import ShoppingCart.domain.GBP;
import ShoppingCart.domain.ProductID;

public class ProductRepository {
  public static final ProductID PRODUCT_ID_100001 = new ProductID(100001);
  public static final ProductID PRODUCT_ID_100002 = new ProductID(100002);
  public static final ProductID PRODUCT_ID_200001 = new ProductID(200001);
  public static final ProductID PRODUCT_ID_200110 = new ProductID(200110);

  private static final Map<ProductID, Product> products = new HashMap<ProductID, Product>()
  {
    {
      put(PRODUCT_ID_100001,
         new Product(PRODUCT_ID_100001, BOOK, "Lord of the Rings", new GBP(10.00)));
      put(PRODUCT_ID_100002,
          new Product(PRODUCT_ID_100002, BOOK, "The Hobbit", new GBP(5.00)));
      put(PRODUCT_ID_200001,
          new Product(PRODUCT_ID_200001, DVD, "Game of Thrones", new GBP(9.00)));
      put(PRODUCT_ID_200110,
          new Product(PRODUCT_ID_200110, DVD, "Breaking Bad", new GBP(7.00)));
    }
  };

  public Product getProductBy(ProductID productId) {
    return products.get(productId);
  }
}
