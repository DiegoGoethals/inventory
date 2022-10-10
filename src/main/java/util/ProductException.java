package util;

public class ProductException extends RuntimeException{

  public ProductException(String message) {
    super(message);
  }

  public ProductException(String message, Throwable t) {
    super(message, t);
  }

}
