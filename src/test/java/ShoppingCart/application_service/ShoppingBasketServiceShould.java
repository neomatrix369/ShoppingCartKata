package ShoppingCart.application_service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import ShoppingCart.domain.Basket;
import ShoppingCart.domain.BasketItem;
import ShoppingCart.domain.ProductID;
import ShoppingCart.domain.UserID;
import ShoppingCart.infrastructure.BasketRepository;
import ShoppingCart.infrastructure.Clock;
import ShoppingCart.infrastructure.ProductRepository;

public class ShoppingBasketServiceShould {

  private static final ProductID BOOK_THE_HOBBIT = new ProductID(100001);
  private static final ProductID DVD_BREAKING_BAD = new ProductID(200110);

  private Clock clock;
  private BasketRepository basketRepository;
  private ShoppingBasketService shoppingBasketService;

  private UserID userOne;
  private UserID userTwo;
  private ProductRepository productRepository;

  @Before
  public void initialise() {
    clock = new Clock();
    basketRepository = new BasketRepository();
    productRepository = new ProductRepository();
    shoppingBasketService = new ShoppingBasketService(clock, basketRepository, productRepository);
    userOne = new UserID(1);
    userTwo = new UserID(2);
  }

  @Test public void
  contain_the_items_that_are_added_to_the_basket_for_a_specific_user() {
    Basket expectedBasket = new Basket(clock.getCurrentDate(), productRepository,
        new BasketItem(BOOK_THE_HOBBIT, 2), new BasketItem(DVD_BREAKING_BAD, 5));

    final Basket basketBeforeAddingItems = shoppingBasketService.basketFor(userOne);
    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 2);
    shoppingBasketService.addItem(userOne, DVD_BREAKING_BAD, 5);
    final Basket basketAfterAddingItems = shoppingBasketService.basketFor(userOne);

    assertThat(basketBeforeAddingItems, is(nullValue()));
    assertThat(basketAfterAddingItems, is(expectedBasket));
  }
  
  @Test public void
  create_a_basket_when_the_first_product_is_added_to_it() {
    final Basket emptyBasket = shoppingBasketService.basketFor(userOne);
    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 2);
    final Basket nonEmptyBasket = shoppingBasketService.basketFor(userOne);

    assertThat(emptyBasket, is(nullValue()));
    assertThat(nonEmptyBasket, is(notNullValue()));
  }
  
  @Test public void
  contain_the_current_date_as_creation_date_when_a_basket_is_created_for_a_user() {
    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 3);

    final Basket basket = shoppingBasketService.basketFor(userOne);

    assertThat(basket.getCreationDate(), is(equalTo(clock.getCurrentDate())));
  } 
  
  @Test public void
  contain_total_price_of_the_respective_items_of_a_basket_for_a_user() {
    Basket expectedBasket = new Basket(clock.getCurrentDate(), productRepository);
    expectedBasket = expectedBasket.addItem(new BasketItem(BOOK_THE_HOBBIT, 3))
        .addItem(new BasketItem(DVD_BREAKING_BAD, 2));

    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 3);
    shoppingBasketService.addItem(userOne, DVD_BREAKING_BAD, 2);

    Basket basket = shoppingBasketService.basketFor(userOne);
    assertThat(basket.getTotal(), is(equalTo(expectedBasket.getTotal())));
  } 
  
  @Test public void
  store_each_users_basket_separately() {
    Basket expectedBasketForUserOne =
        new Basket(clock.getCurrentDate(), productRepository,
            new BasketItem(BOOK_THE_HOBBIT, 2), new BasketItem(DVD_BREAKING_BAD, 5));
    Basket expectedBasketForUserTwo =
        new Basket(clock.getCurrentDate(), productRepository, new BasketItem(DVD_BREAKING_BAD, 5));

    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 2);
    shoppingBasketService.addItem(userOne, DVD_BREAKING_BAD, 5);
    shoppingBasketService.addItem(userTwo, DVD_BREAKING_BAD, 5);

    assertThat(shoppingBasketService.basketFor(userOne), is(expectedBasketForUserOne));
    assertThat(shoppingBasketService.basketFor(userTwo), is(expectedBasketForUserTwo));
  }
}
