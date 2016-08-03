package ShoppingCart;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class BasketItem {
  private final ProductID productID;
  private final int quantity;

  public BasketItem(ProductID productID, int quantity) {
    this.productID = productID;
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    BasketItem that = (BasketItem) o;

    return new EqualsBuilder()
        .append(quantity, that.quantity)
        .append(productID, that.productID)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(productID)
        .append(quantity)
        .toHashCode();
  }
}
