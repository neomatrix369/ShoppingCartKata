package ShoppingCart.domain;

public class UserID {
  private int id;

  public UserID(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "" + id;
  }
}
