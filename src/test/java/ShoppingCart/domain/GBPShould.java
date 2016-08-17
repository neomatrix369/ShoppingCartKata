package ShoppingCart.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GBPShould {

  private static final GBP FIVE_POUNDS = new GBP(5.00);
  private static final GBP FIFTY_POUNDS = new GBP(50.00);

  @Test public void
  return_a_multiple_of_the_original_value() {
    assertThat(FIVE_POUNDS.multiplyBy(10), is(FIFTY_POUNDS));
  }
}
