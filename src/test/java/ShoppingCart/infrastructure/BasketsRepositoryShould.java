package ShoppingCart.infrastructure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ShoppingCart.domain.Basket;
import ShoppingCart.domain.BasketItem;
import ShoppingCart.domain.ProductID;
import ShoppingCart.domain.UserID;

public class BasketsRepositoryShould {
  @Test public void
  add_a_basket_for_any_user() {
    ProductRepository productRepository = new ProductRepository();
    Clock clock = new Clock();
    List<BasketItem> basketItems = new ArrayList<>();
    basketItems.add(new BasketItem(new ProductID(100001), 2));
    Basket anyBasket = new Basket(basketItems, clock.getCurrentDate(), productRepository);
    BasketsRepository basketRepository = new BasketsRepository();
    UserID userOne = new UserID(1);

    basketRepository.addBasketFor(userOne, anyBasket);
    Basket actualBasket = basketRepository.getBasketFor(userOne);

    assertThat(actualBasket, is(anyBasket));
  } 
}
