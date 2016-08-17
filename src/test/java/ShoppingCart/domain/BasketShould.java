package ShoppingCart.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import ShoppingCart.infrastructure.Clock;
import ShoppingCart.infrastructure.ProductRepository;

public class BasketShould {

  private static final ProductID PRODUCT_ID_100001 = new ProductID(100001);
  private static final GBP TWENTY_POUNDS = new GBP(20);

  private ProductRepository productRepository;
  private Clock clock;
  private Basket basket;

  @Before
  public void initialise() {
    productRepository = new ProductRepository();
    clock = new Clock();
    basket = new Basket(clock.getCurrentDate(), productRepository);
  }

  @Test public void
  calculate_the_total_when_items_are_added_to_it() {
    basket = basket.addItem(new BasketItem(PRODUCT_ID_100001, 2));

    assertThat(basket.getTotal(), is(equalTo(TWENTY_POUNDS)));
  }
}
