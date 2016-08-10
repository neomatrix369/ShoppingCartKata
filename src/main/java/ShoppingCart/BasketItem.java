package ShoppingCart;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class BasketItem {
  private final ProductID productId;
  private final int quantity;

  public BasketItem(ProductID productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public ProductID getProductId() {
    return productId;
  }

  public GBP getTotalFor(Product product) {
    return product.priceMultiplyBy(quantity);
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    BasketItem that = (BasketItem) o;

    return new EqualsBuilder()
        .append(quantity, that.quantity)
        .append(productId, that.productId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(productId)
        .append(quantity)
        .toHashCode();
  }
}
