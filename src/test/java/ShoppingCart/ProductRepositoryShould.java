package ShoppingCart;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static ShoppingCart.Category.*;
import static ShoppingCart.Category.BOOK;
import static ShoppingCart.Money.GBP;
import static ShoppingCart.ProductRepository.PRODUCT_ID_100001;
import static ShoppingCart.ProductRepository.PRODUCT_ID_100002;
import static ShoppingCart.ProductRepository.PRODUCT_ID_200001;
import static ShoppingCart.ProductRepository.getProductBy;

import org.junit.Test;

public class ProductRepositoryShould {

  @Test public void
  return_the_respective_books_and_dvds_with_titles_and_prices_by_product_id() {
    assertThat(getProductBy(PRODUCT_ID_100001),
        is(new Product(PRODUCT_ID_100001, BOOK, "Lord of the Rings", GBP(10.00))));

    assertThat(getProductBy(PRODUCT_ID_100002),
        is(new Product(PRODUCT_ID_100002, BOOK, "The Hobbit", GBP(5.00))));

    assertThat(getProductBy(PRODUCT_ID_200001),
        is(new Product(PRODUCT_ID_200001, DVD, "Game of Thrones", GBP(9.00))));
  }
}
