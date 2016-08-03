package ShoppingCart;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Product {
  private final ProductID productID;
  private final String title;
  private final Money price;

  public Product(ProductID productID, Category category, String title, Money price) {
    this.productID = productID;
    this.title = title;
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Product product = (Product) o;

    return new EqualsBuilder()
        .append(productID, product.productID)
        .append(title, product.title)
        .append(price, product.price)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(productID)
        .append(title)
        .append(price)
        .toHashCode();
  }
}
