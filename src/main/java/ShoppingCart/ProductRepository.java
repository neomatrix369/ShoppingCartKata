package ShoppingCart;

import static ShoppingCart.ProductCategory.BOOK;
import static ShoppingCart.ProductCategory.DVD;

import java.util.HashMap;
import java.util.Map;

public  class ProductRepository {
  private static final ProductID PRODUCT_ID_10001 = new ProductID(10001);
  private static final ProductID PRODUCT_ID_10002 = new ProductID(10002);
  private static final ProductID PRODUCT_ID_20001 = new ProductID(20001);
  private static final ProductID PRODUCT_ID_20110 = new ProductID(20110);

  private static Map<ProductID, Product> items = new HashMap<ProductID, Product>()
  {
    {put(PRODUCT_ID_10001, new Product(PRODUCT_ID_10001, BOOK, "Lord of the Rings", 10.00));}
    {put(PRODUCT_ID_10002, new Product(PRODUCT_ID_10002, BOOK, "The Hobbit", 5.00));}
    {put(PRODUCT_ID_20001, new Product(PRODUCT_ID_20001, DVD, "Game of Thrones", 9.00));}
    {put(PRODUCT_ID_20110, new Product(PRODUCT_ID_20110, DVD, "Breaking Bad", 7.00));}
  };

  public static Product forId(ProductID id) {
    return items.get(id);
  }
}
