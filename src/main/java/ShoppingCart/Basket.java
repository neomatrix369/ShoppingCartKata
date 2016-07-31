package ShoppingCart;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Basket {
  private LocalDateTime creationDate;
  private Map<ProductID, Integer> items = new HashMap<>();
  private double total = 0.0;

  public void setTotal(double total) {
    this.total = total;
  }

  public void addItem(Item item) {
  }

  public void setCreationDate(LocalDateTime date) {
    this.creationDate = date;
  }
}
