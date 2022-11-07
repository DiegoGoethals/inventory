package domain;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Product {

  private final String name;
  private int quantity;
  private final ImageView image;

  public Product(String name, int quantity, String imageURL) {
    this.name = name;
    this.quantity = quantity;
    if (imageURL != null) {
      image = new ImageView(new Image(imageURL, true));
    } else {
      image = new ImageView(new Image(String.valueOf(getClass().getResource("/images/no_image_available.png"))));
    }
    image.setFitWidth(64);
    image.setFitHeight(64);
    image.setPreserveRatio(true);
  }

  public String getName() {
    return name;
  }

  public int getQuantity() {
    return quantity;
  }

  public ImageView getImage() {
    return image;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return quantity == product.quantity && Objects.equals(name, product.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, quantity);
  }

  @Override
  public String toString() {
    return name + ": " + quantity;
  }
}
