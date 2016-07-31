package ShoppingCart;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static java.time.LocalDate.now;
import static ShoppingCart.ProductCategory.BOOK;
import static ShoppingCart.ProductCategory.DVD;

import org.junit.Before;
import org.junit.Test;

public class ShoppingBasketShould {

  private static final ProductID LORD_OF_THE_RINGS = new ProductID(10001);
  private static final ProductID THE_HOBBIT = new ProductID(10002);
  private static final ProductID GAME_OF_THRONES = new ProductID(20001);
  private static final ProductID BREAKING_BAD = new ProductID(20110);

  private UserID userOne;
  private ShoppingBasketService shoppingBasket;

  @Before
  public void initialise() {
    userOne = new UserID("UserOne");
    shoppingBasket = new ShoppingBasketService();
  }

  @Test
  public void should_contain_items_in_a_basket_for_a_user_after_items_are_added_to_it() {
    Basket expectedBasket = new Basket();
    expectedBasket.setCreationDate(now());
    expectedBasket.addItem(new Item(THE_HOBBIT, 2));
    expectedBasket.addItem(new Item(BREAKING_BAD, 5));
    expectedBasket.setTotal(45.00);

    shoppingBasket.addItem(userOne, THE_HOBBIT, 2);
    shoppingBasket.addItem(userOne, BREAKING_BAD, 5);

    assertThat(shoppingBasket.basketFor(userOne), is(equalTo(expectedBasket)));
  }

  @Test
  public void create_a_basket_only_when_the_first_item_is_added_by_the_user() {
    assertThat(shoppingBasket.basketFor(userOne), is(nullValue()));
    shoppingBasket.addItem(userOne, LORD_OF_THE_RINGS, 3);
    assertThat(shoppingBasket.basketFor(userOne), is(notNullValue()));
  }

  @Test
  public void have_an_in_memory_products_repository() {
    assertThat(ProductRepository.getProductForId(LORD_OF_THE_RINGS), is(new Product(LORD_OF_THE_RINGS, BOOK, "Lord of the Rings", 10.00)));
    assertThat(ProductRepository.getProductForId(THE_HOBBIT), is(new Product(THE_HOBBIT, BOOK, "The Hobbit", 5.00)));
    assertThat(ProductRepository.getProductForId(GAME_OF_THRONES), is(new Product(GAME_OF_THRONES, DVD, "Game of Thrones", 9.00)));
    assertThat(ProductRepository.getProductForId(BREAKING_BAD), is(new Product(BREAKING_BAD, DVD, "Breaking Bad", 7.00)));
  }
}
