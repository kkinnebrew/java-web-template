package common;

import application.exceptions.InvalidInputException;

import java.math.BigDecimal;

/**
 * @author kkinnebrew
 */
public class Validate {

  public static void notNullOrWhitespace(String value, String message) throws InvalidInputException {
    if (value == null || value.isEmpty()) {
      throw new InvalidInputException(message);
    }
  }

  public static void notNull(Object value, String message) throws InvalidInputException {
    if (value == null) {
      throw new InvalidInputException(message);
    }
  }

  public static void notNegative(BigDecimal value, String message) throws InvalidInputException {
    if (value != null && value.compareTo(BigDecimal.ZERO) < 0) {
      throw new InvalidInputException(message);
    }
  }

  public static void notZero(BigDecimal value, String message) throws InvalidInputException {
    if (value != null && value.compareTo(BigDecimal.ZERO) == 0) {
      throw new InvalidInputException(message);
    }
  }

  public static void notZeroOrNegative(BigDecimal value, String message) throws InvalidInputException {
    if (value != null && value.compareTo(BigDecimal.ZERO) <= 0) {
      throw new InvalidInputException(message);
    }
  }

}
