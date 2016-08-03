package ShoppingCart;

import java.time.LocalDate;
import java.util.List;

public class Basket {
  private final List<BasketItem> items;
  private final LocalDate date;

  public Basket(List<BasketItem> items, LocalDate date) {
    this.items = items;
    this.date = date;
  }
}
