package ShoppingCart.infrastructure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static ShoppingCart.domain.Category.BOOK;
import static ShoppingCart.domain.Category.DVD;
import static ShoppingCart.infrastructure.ProductRepository.PRODUCT_ID_100001;
import static ShoppingCart.infrastructure.ProductRepository.PRODUCT_ID_100002;
import static ShoppingCart.infrastructure.ProductRepository.PRODUCT_ID_200001;
import static ShoppingCart.infrastructure.ProductRepository.PRODUCT_ID_200110;

import org.junit.Before;
import org.junit.Test;

import ShoppingCart.domain.GBP;
import ShoppingCart.domain.Product;

public class ProductRepositoryShould {

  private ProductRepository productRepository;


  @Before
  public void initialise() {
    productRepository = new ProductRepository();
  }

  @Test public void
  return_the_respective_books_and_dvds_with_titles_and_prices_by_product_id() {
    assertThat(productRepository.getProductBy(PRODUCT_ID_100001),
        is(new Product(PRODUCT_ID_100001, BOOK, "Lord of the Rings", new GBP(10.00))));

    assertThat(productRepository.getProductBy(PRODUCT_ID_100002),
        is(new Product(PRODUCT_ID_100002, BOOK, "The Hobbit", new GBP(5.00))));

    assertThat(productRepository.getProductBy(PRODUCT_ID_200001),
        is(new Product(PRODUCT_ID_200001, DVD, "Game of Thrones", new GBP(9.00))));

    assertThat(productRepository.getProductBy(PRODUCT_ID_200110),
        is(new Product(PRODUCT_ID_200110, DVD, "Breaking Bad", new GBP(7.00))));
  }
}
