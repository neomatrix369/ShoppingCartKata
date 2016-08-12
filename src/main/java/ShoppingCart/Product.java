package ShoppingCart;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Product {
  private final ProductID productID;
  private final Category category;
  private final String title;
  private final GBP price;

  public Product(ProductID productID, Category category, String title, GBP price) {
    this.productID = productID;
    this.category = category;
    this.title = title;
    this.price = price;
  }

  public GBP getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Product product = (Product) o;

    return new EqualsBuilder()
        .append(productID, product.productID)
        .append(category, product.category)
        .append(title, product.title)
        .append(price, product.price)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(productID)
        .append(category)
        .append(title)
        .append(price)
        .toHashCode();
  }
}
