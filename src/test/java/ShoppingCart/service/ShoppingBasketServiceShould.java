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

import ShoppingCart.domain.Basket;
import ShoppingCart.domain.BasketItem;
import ShoppingCart.domain.GBP;
import ShoppingCart.domain.OutOfStockException;
import ShoppingCart.domain.ProductID;
import ShoppingCart.domain.UserID;
import ShoppingCart.infrastructure.BasketsRepository;
import ShoppingCart.infrastructure.Clock;
import ShoppingCart.infrastructure.Console;
import ShoppingCart.infrastructure.ProductRepository;

public class ShoppingBasketServiceShould {

  private static final ProductID BOOK_THE_HOBBIT = new ProductID(100002);
  private static final ProductID DVD_BREAKING_BAD = new ProductID(200110);

  private Clock clock;
  private ShoppingBasketService shoppingBasketService;

  private UserID userOne;
  private UserID userTwo;
  private UserID userThree;
  private ProductRepository productRepository;
  private Console console;
  private StockService stockService;

  @Before
  public void initialise() {
    clock = new Clock();
    console = mock(Console.class);
    productRepository = new ProductRepository();
    stockService = new StockService();
    shoppingBasketService = new ShoppingBasketService(
            console, clock, new BasketsRepository(), productRepository, stockService);
    userOne = new UserID(1);
    userTwo = new UserID(2);
    userThree = new UserID(3);

    stockService.updateStock(BOOK_THE_HOBBIT, 5);
    stockService.updateStock(DVD_BREAKING_BAD, 10);
  }

  @Test public void
  contain_the_items_that_are_added_to_the_basket_when_it_is_checked_out()
      throws OutOfStockException {
    List<BasketItem> items =
        createBasketItems(new BasketItem(BOOK_THE_HOBBIT, 2), new BasketItem(DVD_BREAKING_BAD, 5));
    Basket expectedBasket = new Basket(items, clock.getCurrentDate(), productRepository);

    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 2);
    shoppingBasketService.addItem(userOne, DVD_BREAKING_BAD, 5);

    assertThat(shoppingBasketService.basketFor(userOne), is(expectedBasket));
  }
  
  @Test public void
  create_a_basket_when_the_first_product_is_added_to_it() throws OutOfStockException {
    final Basket emptyBasket = shoppingBasketService.basketFor(userOne);
    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 2);
    final Basket nonEmptyBasket = shoppingBasketService.basketFor(userOne);

    assertThat(emptyBasket, is(nullValue()));
    assertThat(nonEmptyBasket, is(notNullValue()));
  }
  
  @Test public void
  contain_the_current_date_as_creation_date_when_a_basket_is_created_for_a_user()
      throws OutOfStockException {
    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 3);

    final Basket basket = shoppingBasketService.basketFor(userOne);
    assertThat(basket.getCreationDate(), is(equalTo(clock.getCurrentDate())));
  } 
  
  @Test public void
  contain_total_of_the_respective_items_when_added_to_the_basket_is_created_for_a_user()
      throws OutOfStockException {
    Basket expectedBasket = new Basket(clock.getCurrentDate(), productRepository);
    expectedBasket = expectedBasket.addItem(new BasketItem(BOOK_THE_HOBBIT, 3));

    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 3);

    Basket basket = shoppingBasketService.basketFor(userOne);
    assertThat(basket.getTotal(), is(equalTo(expectedBasket.getTotal())));
  } 
  
  @Test public void
  store_each_users_basket_separately() throws OutOfStockException {
    Basket expectedBasketForUserOne =
        new Basket(createBasketItems(new BasketItem(BOOK_THE_HOBBIT, 2),
            new BasketItem(DVD_BREAKING_BAD, 5)), clock.getCurrentDate(), productRepository);
    Basket expectedBasketForUserTwo =
        new Basket(createBasketItems(
            new BasketItem(DVD_BREAKING_BAD, 5)), clock.getCurrentDate(), productRepository);

    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 2);
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
    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 2);

    verify(console).print(
        format("[BASKET CREATED]: Created[\"%s\"], User[%s]", clock.getCurrentDate(), userOne));
  }

  @Test public void
  log_to_the_console_when_item_is_added_to_the_basket() throws OutOfStockException {
    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 3);

    verify(console).print(
        format("[ITEM ADDED TO SHOPPING CART]: Added[\"%s\"], User[%s], Product[%s], Quantity[%d], Price[%s]",
            clock.getCurrentDate(), userOne, BOOK_THE_HOBBIT, 3, productRepository.getProductBy(BOOK_THE_HOBBIT).getPrice()));
  }
  
  @Test (expected = OutOfStockException.class) public void
  throw_an_exception_if_ordered_item_is_not_in_stock() throws OutOfStockException {
    stockService.updateStock(DVD_BREAKING_BAD, 0);
    shoppingBasketService.addItem(userOne, DVD_BREAKING_BAD, 2);
  }
  
  @Test (expected = OutOfStockException.class) public void
  deduct_the_items_from_the_stock_when_added_to_the_basket_and_throw_an_exception_if_quantity_exceeds_stock_count()
      throws OutOfStockException {
    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 2);
    shoppingBasketService.addItem(userTwo, BOOK_THE_HOBBIT, 3);
    shoppingBasketService.addItem(userThree, BOOK_THE_HOBBIT, 1);
  }

  @Test public void
  calculate_a_total_less_10_percent_if_more_than_3_books_are_in_the_cart()
      throws OutOfStockException {
    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 4);

    Basket basket = shoppingBasketService.basketFor(userOne);
    assertThat(basket.getTotal(), is(new GBP(18.00)));
  }

  @Test public void
  calculate_a_total_with_no_discount_2_books_are_in_the_cart()
      throws OutOfStockException {
    shoppingBasketService.addItem(userTwo, BOOK_THE_HOBBIT, 2);

    Basket basket = shoppingBasketService.basketFor(userTwo);
    assertThat(basket.getTotal(), is(new GBP(10.00)));
  }

  @Test public void
  calculate_a_total_less_20_percent_if_1_book_and_1_video_are_in_the_cart()
      throws OutOfStockException {
    shoppingBasketService.addItem(userOne, BOOK_THE_HOBBIT, 1);
    shoppingBasketService.addItem(userOne, DVD_BREAKING_BAD, 1);

    Basket basket = shoppingBasketService.basketFor(userOne);
    assertThat(basket.getTotal(), is(new GBP(9.60)));
  }
}
