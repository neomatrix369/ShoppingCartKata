package ShoppingCart;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static java.time.LocalDate.now;
import static ShoppingCart.Amount.£;
import static ShoppingCart.ProductCategory.BOOK;
import static ShoppingCart.ProductCategory.DVD;
import static ShoppingCart.ProductRepository.getProductForId;

import org.junit.Before;
import org.junit.Test;

public class ShoppingBasketShould {

  private static final ProductID LORD_OF_THE_RINGS = new ProductID(10001);
  private static final ProductID THE_HOBBIT = new ProductID(10002);
  private static final ProductID GAME_OF_THRONES = new ProductID(20001);
  private static final ProductID BREAKING_BAD = new ProductID(20110);

  private UserID userOne;
  private UserID userTwo;
  private ShoppingBasketService shoppingBasket;

  @Before
  public void initialise() {
    userOne = new UserID("UserOne");
    userTwo = new UserID("UserTwo");
    shoppingBasket = new ShoppingBasketService();
  }

  @Test
  public void contain_items_in_a_basket_for_a_user_after_items_are_added_to_it() {
    Basket expectedBasket =
        createBasketWithItems(£(45.00), new BasketItem(THE_HOBBIT, 2), new BasketItem(BREAKING_BAD, 5));

    shoppingBasket.addItem(userOne, THE_HOBBIT, 2);
    shoppingBasket.addItem(userOne, BREAKING_BAD, 5);

    assertThat(shoppingBasket.basketFor(userOne), is(equalTo(expectedBasket)));
  }

  @Test
  public void have_separate_baskets_for_each_user() {
    Basket expectedUserOneBasket = createBasketWithItems(£(21.00), new BasketItem(BREAKING_BAD, 3));
    Basket expectedUserTwoBasket = createBasketWithItems(£(20.00), new BasketItem(THE_HOBBIT, 4));

    shoppingBasket.addItem(userOne, BREAKING_BAD, 3);
    shoppingBasket.addItem(userTwo, THE_HOBBIT, 4);

    assertThat(shoppingBasket.basketFor(userOne), is(equalTo(expectedUserOneBasket)));
    assertThat(shoppingBasket.basketFor(userTwo), is(equalTo(expectedUserTwoBasket)));
  }

  @Test
  public void create_a_basket_only_when_the_first_item_is_added_by_the_user() {
    assertThat(shoppingBasket.basketFor(userOne), is(nullValue()));
    shoppingBasket.addItem(userOne, LORD_OF_THE_RINGS, 3);
    assertThat(shoppingBasket.basketFor(userOne), is(notNullValue()));
  }

  @Test
  public void have_an_in_memory_products_repository() {
    assertThat(getProductForId(LORD_OF_THE_RINGS), is(new Product(LORD_OF_THE_RINGS, BOOK, "Lord of the Rings", £(10.00))));
    assertThat(getProductForId(THE_HOBBIT), is(new Product(THE_HOBBIT, BOOK, "The Hobbit", £( 5.00))));
    assertThat(getProductForId(GAME_OF_THRONES), is(new Product(GAME_OF_THRONES, DVD, "Game of Thrones", £(9.00))));
    assertThat(getProductForId(BREAKING_BAD), is(new Product(BREAKING_BAD, DVD, "Breaking Bad", £(7.00))));
  }

  private Basket createBasketWithItems(Amount basketTotal, BasketItem... basketItems) {
    Basket expectedBasket = new Basket();
    expectedBasket.setCreationDate(now());
    for (BasketItem basketItem : basketItems) {
      expectedBasket.addItem(basketItem);
    }
    expectedBasket.setTotal(basketTotal);
    return expectedBasket;
  }
}
