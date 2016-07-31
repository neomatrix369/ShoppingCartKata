package ShoppingCart;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Basket {
  private LocalDateTime creationDate;
  private List<Item> items = new ArrayList<>();
  private double total = 0.0;

  public void setTotal(double total) {
    this.total = total;
  }

  public void addItem(Item item) {
    items.add(item);
  }

  public void setCreationDate(LocalDateTime date) {
    this.creationDate = date;
  }
}
