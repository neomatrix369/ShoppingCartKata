package ShoppingCart;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static ShoppingCart.Money.GBP;
import static ShoppingCart.ProductRepository.PRODUCT_ID_100001;

import org.junit.Test;

public class ProductRepositoryShould {

  @Test public void
  return_the_respective_books_and_dvds_with_titles_and_prices_by_product_id() {
    assertThat(ProductRepository.getProductBy(PRODUCT_ID_100001),
        is(new Product(PRODUCT_ID_100001, "Lord of the Rings", GBP(10.00))));
  }
}
