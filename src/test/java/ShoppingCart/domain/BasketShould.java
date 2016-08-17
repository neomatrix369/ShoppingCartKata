package ShoppingCart.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import ShoppingCart.infrastructure.Clock;
import ShoppingCart.infrastructure.ProductRepository;

public class BasketShould {
  @Test public void
  calculate_the_total_when_items_are_added_to_it() {
    ProductRepository productRepository = new ProductRepository();
    Clock clock = new Clock();
    Basket basket = new Basket(clock.getCurrentDate(), productRepository);

    basket.addItem(new BasketItem(new ProductID(100001), 2));

    assertThat(basket.getTotal(), is(equalTo(new GBP(20))));
  }
}
