package ShoppingCart;

import static java.lang.String.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ProductID {
  private final int id;

  public ProductID(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    ProductID productID = (ProductID) o;

    return new EqualsBuilder()
        .append(id, productID.id)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id)
        .toHashCode();
  }

  @Override
  public String toString() {
    return format("ProductID{id=%d}", id);
  }
}
