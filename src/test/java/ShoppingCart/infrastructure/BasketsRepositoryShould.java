package ShoppingCart.infrastructure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ShoppingCart.domain.Basket;
import ShoppingCart.domain.BasketItem;
import ShoppingCart.domain.ProductID;
import ShoppingCart.domain.UserID;

public class BasketsRepositoryShould {

  private ProductRepository productRepository;
  private BasketRepository basketRepository;
  private UserID userOne;

  @Before
  public void initialise() {
    productRepository = new ProductRepository();
    basketRepository = new BasketRepository();
    userOne = new UserID(1);
  }

  @Test public void
  add_a_basket_for_any_user() {
    List<BasketItem> basketItems = new ArrayList<>();
    basketItems.add(new BasketItem(new ProductID(100001), 2));
    final Basket anyBasket = new Basket(basketItems, new Clock().getCurrentDate(), productRepository);

    basketRepository.addBasketFor(userOne, anyBasket);

    assertThat(basketRepository.getBasketFor(userOne), is(anyBasket));
  } 
}
