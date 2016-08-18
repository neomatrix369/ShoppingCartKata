package ShoppingCart.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ShoppingCart.Console;
import ShoppingCart.domain.Basket;
import ShoppingCart.domain.BasketItem;
import ShoppingCart.domain.OutOfStockException;
import ShoppingCart.domain.ProductID;
import ShoppingCart.domain.UserID;
import ShoppingCart.infrastructure.BasketsRepository;
import ShoppingCart.infrastructure.Clock;
import ShoppingCart.infrastructure.ProductRepository;

public class ShoppingBasketServiceShould {

  private static final ProductID DVD_THE_HOBBIT = new ProductID(100001);
  private static final ProductID DVD_BREAKING_BAD = new ProductID(200110);

  private Clock clock;
  private ShoppingBasketService shoppingBasketService;

  private UserID userOne;
  private UserID userTwo;
  private ProductRepository productRepository;
  private Console console;
  private StockService stockService;

  @Before
  public void initialise() {
    clock = new Clock();
    console = mock(Console.class);
    productRepository = new ProductRepository();
    stockService = new StockService();
    shoppingBasketService =
        new ShoppingBasketService(console, clock, new BasketsRepository(), productRepository, stockService);
    userOne = new UserID(1);
    userTwo = new UserID(2);
  }

  @Test public void
  contain_the_items_that_are_added_to_the_basket_when_it_is_checked_out()
      throws OutOfStockException {
    List<BasketItem> items =
        createBasketItems(new BasketItem(DVD_THE_HOBBIT, 2), new BasketItem(DVD_BREAKING_BAD, 5));
    Basket expectedBasket = new Basket(items, clock.getCurrentDate(), productRepository);

    shoppingBasketService.addItem(userOne, DVD_THE_HOBBIT, 2);
    shoppingBasketService.addItem(userOne, DVD_BREAKING_BAD, 5);

    assertThat(shoppingBasketService.basketFor(userOne), is(expectedBasket));
  }
  
  @Test public void
  create_a_basket_when_the_first_product_is_added_to_it() throws OutOfStockException {
    final Basket emptyBasket = shoppingBasketService.basketFor(userOne);
    shoppingBasketService.addItem(userOne, DVD_THE_HOBBIT, 2);
    final Basket nonEmptyBasket = shoppingBasketService.basketFor(userOne);

    assertThat(emptyBasket, is(nullValue()));
    assertThat(nonEmptyBasket, is(notNullValue()));
  }
  
  @Test public void
  contain_the_current_date_as_creation_date_when_a_basket_is_created_for_a_user()
      throws OutOfStockException {
    shoppingBasketService.addItem(userOne, DVD_THE_HOBBIT, 3);

    final Basket basket = shoppingBasketService.basketFor(userOne);
    assertThat(basket.getCreationDate(), is(equalTo(clock.getCurrentDate())));
  } 
  
  @Test public void
  contain_total_of_the_respective_items_when_added_to_the_basket_is_created_for_a_user()
      throws OutOfStockException {
    Basket expectedBasket = new Basket(clock.getCurrentDate(), productRepository);
    expectedBasket = expectedBasket.addItem(new BasketItem(DVD_THE_HOBBIT, 3));

    shoppingBasketService.addItem(userOne, DVD_THE_HOBBIT, 3);

    Basket basket = shoppingBasketService.basketFor(userOne);
    assertThat(basket.getTotal(), is(equalTo(expectedBasket.getTotal())));
  } 
  
  @Test public void
  store_each_users_basket_separately() throws OutOfStockException {
    Basket expectedBasketForUserOne =
        new Basket(createBasketItems(new BasketItem(DVD_THE_HOBBIT, 2),
            new BasketItem(DVD_BREAKING_BAD, 5)), clock.getCurrentDate(), productRepository);
    Basket expectedBasketForUserTwo =
        new Basket(createBasketItems(
            new BasketItem(DVD_BREAKING_BAD, 5)), clock.getCurrentDate(), productRepository);

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

  @Test public void
  log_to_the_console_when_a_basket_is_created() throws OutOfStockException {
    shoppingBasketService.addItem(userOne, DVD_THE_HOBBIT, 2);

    verify(console).print(
        format("[BASKET CREATED]: Created[\"%s\"], User[%s]", clock.getCurrentDate(), userOne));
  }

  @Test public void
  log_to_the_console_when_item_is_added_to_the_basket() throws OutOfStockException {
    shoppingBasketService.addItem(userOne, DVD_THE_HOBBIT, 3);

    verify(console).print(
        format("[ITEM ADDED TO SHOPPING CART]: Added[\"%s\"], User[%s], Product[%s], Quantity[%d], Price[%s]",
            clock.getCurrentDate(), userOne, DVD_THE_HOBBIT, 3, productRepository.getProductBy(DVD_THE_HOBBIT).getPrice()));
  }
  
  @Test (expected = OutOfStockException.class) public void
  throw_an_exception_if_there_isnt_enough_items_available_in_stock() throws OutOfStockException {
    shoppingBasketService.addItem(userOne, DVD_BREAKING_BAD, 2);
  } 
}
