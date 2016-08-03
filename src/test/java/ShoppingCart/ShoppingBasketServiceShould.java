package ShoppingCart;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static java.time.LocalDate.now;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ShoppingBasketServiceShould {

  private static final ProductID DVD_THE_HOBBIT = new ProductID(10001);
  private static final ProductID DVD_BREAKING_BAD = new ProductID(20110);

  private ShoppingBasketService shoppingBasketService;
  private UserID userOne;
  private Clock clock;
  private BasketsRepository basketRepository;

  @Before
  public void initialise() {
    clock = new Clock();
    basketRepository = new BasketsRepository();
    shoppingBasketService = new ShoppingBasketService(clock, basketRepository);
    userOne = new UserID();
  }

  @Test public void
  contain_the_items_that_are_added_to_the_basket_when_it_is_checked_out() {
    List<BasketItem> items = new ArrayList<>();
    items.add(new BasketItem(DVD_THE_HOBBIT, 2));
    items.add(new BasketItem(DVD_BREAKING_BAD, 5));

    shoppingBasketService.addItem(userOne, DVD_THE_HOBBIT, 2);
    shoppingBasketService.addItem(userOne, DVD_BREAKING_BAD, 5);

    Basket expectedBasket = new Basket(items, now());
    assertThat(shoppingBasketService.basketFor(userOne), is(expectedBasket));
  }
  
  @Test public void
  create_a_basket_when_the_first_product_is_added_to_it() {
    final Basket emptyBasket = shoppingBasketService.basketFor(userOne);
    shoppingBasketService.addItem(userOne, DVD_THE_HOBBIT, 2);
    final Basket nonEmptyBasket = shoppingBasketService.basketFor(userOne);

    assertThat(emptyBasket, is(nullValue()));
    assertThat(nonEmptyBasket, is(notNullValue()));
  }
}
