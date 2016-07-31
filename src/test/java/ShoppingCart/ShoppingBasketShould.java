package ShoppingCart;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static java.time.LocalDateTime.now;

import org.junit.Before;
import org.junit.Test;

public class ShoppingBasketShould {

  private UserID userOne;
  private ProductRepository products;
  private ShoppingBasketService shoppingBasket;

  @Before
  public void initialise() {
    userOne = new UserID("UserOne");
    products = new ProductRepository();
    shoppingBasket = new ShoppingBasketService();
  }

  @Test
  public void
  should_contain_items_in_a_basket_for_a_user_after_items_are_added_to_it() {
    Basket expectedBasket = new Basket();
    expectedBasket.setCreationDate(now());
    expectedBasket.addItem(new Item(products.forId(10001), 2));
    expectedBasket.addItem(new Item(products.forId(20110), 5));
    expectedBasket.setTotal(45.00);

    shoppingBasket.addItem(userOne, products.forId(10001), 2);
    shoppingBasket.addItem(userOne, products.forId(20110), 5);

    assertThat(shoppingBasket.basketFor(userOne), is(expectedBasket));
  }

  @Test public void
  create_a_basket_only_when_the_first_item_is_added_by_the_user() {
    assertThat(shoppingBasket.basketFor(userOne), is(nullValue()));
    shoppingBasket.addItem(userOne, products.forId(10001), 3);
    assertThat(shoppingBasket.basketFor(userOne), is(notNullValue()));
  }
}
