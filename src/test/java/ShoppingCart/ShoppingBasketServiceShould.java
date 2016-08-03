package ShoppingCart;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ShoppingBasketServiceShould {

  private static final ProductID DVD_THE_HOBBIT = new ProductID(10001);
  private static final ProductID DVD_BREAKING_BAD = new ProductID(20110);

  private Clock clock;
  private BasketsRepository basketRepository;
  private ShoppingBasketService shoppingBasketService;

  private UserID userOne;
  private UserID userTwo;

  @Before
  public void initialise() {
    clock = new Clock();
    basketRepository = new BasketsRepository();
    shoppingBasketService = new ShoppingBasketService(clock, basketRepository);
    userOne = new UserID();
    userTwo = new UserID();
  }

  @Test public void
  contain_the_items_that_are_added_to_the_basket_when_it_is_checked_out() {
    List<BasketItem> items =
        createBasketItems(new BasketItem(DVD_THE_HOBBIT, 2), new BasketItem(DVD_BREAKING_BAD, 5));
    Basket expectedBasket = new Basket(items, now());

    shoppingBasketService.addItem(userOne, DVD_THE_HOBBIT, 2);
    shoppingBasketService.addItem(userOne, DVD_BREAKING_BAD, 5);

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
  
  @Test public void
  store_each_users_basket_separately() {
    Basket expectedBasketForUserOne =
        new Basket(createBasketItems(new BasketItem(DVD_THE_HOBBIT, 2), new BasketItem(DVD_BREAKING_BAD, 5)), now());
    Basket expectedBasketForUserTwo =
        new Basket(createBasketItems(new BasketItem(DVD_BREAKING_BAD, 5)), now());

    shoppingBasketService.addItem(userOne, DVD_THE_HOBBIT, 2);
    shoppingBasketService.addItem(userOne, DVD_BREAKING_BAD, 5);
    shoppingBasketService.addItem(userTwo, DVD_BREAKING_BAD, 5);

    assertThat(shoppingBasketService.basketFor(userOne), is(expectedBasketForUserOne));
    assertThat(shoppingBasketService.basketFor(userTwo), is(expectedBasketForUserTwo));
  }

  private List<BasketItem> createBasketItems(BasketItem... basketItem) {
    List<BasketItem> itemsUserOne = new ArrayList<>();
    itemsUserOne.addAll(asList(basketItem));
    return itemsUserOne;
  }
}
