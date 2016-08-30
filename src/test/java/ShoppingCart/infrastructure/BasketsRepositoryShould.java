package ShoppingCart.infrastructure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import ShoppingCart.domain.Basket;
import ShoppingCart.domain.BasketItem;
import ShoppingCart.domain.ProductID;
import ShoppingCart.domain.UserID;
import ShoppingCart.domain_service.DiscountService;

public class BasketsRepositoryShould {

  private ProductRepository productRepository;
  private BasketRepository basketRepository;
  private UserID userOne;
  private DiscountService discountService;

  @Before
  public void initialise() {
    productRepository = new ProductRepository();
    discountService = new DiscountService(productRepository);
    basketRepository = new BasketRepository();
    userOne = new UserID(1);
  }

  @Test public void
  add_a_basket_for_any_user() {
    final Basket anyBasket = new Basket(
            new Clock().getCurrentDate(),
            productRepository,
            discountService,
            new BasketItem(new ProductID(100001), 2)
    );

    basketRepository.addBasketFor(userOne, anyBasket);

    assertThat(basketRepository.getBasketFor(userOne), is(anyBasket));
  } 
}
