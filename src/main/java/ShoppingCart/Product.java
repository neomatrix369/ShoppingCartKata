package ShoppingCart;

import static ShoppingCart.Amount.£;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Product {
  private final ProductID productID;
  private final ProductCategory category;
  private final String title;
  private final Amount price;

  public Product(ProductID productID, ProductCategory category, String title, Amount price) {
    this.productID = productID;
    this.category = category;
    this.title = title;
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Product product = (Product) o;

    return new EqualsBuilder()
        .append(price, product.price)
        .append(productID, product.productID)
        .append(category, product.category)
        .append(title, product.title)
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

  public Amount getTotalPriceFor(int quantity) {
    return £(quantity * price.getValue());
  }

  @Override
  public String toString() {
    return String.format("Product{productID=%s, category=%s, title='%s', price=%s}", productID, category, title, price);
  }
}
