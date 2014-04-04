package application.exceptions;

import java.lang.reflect.Type;
import java.text.MessageFormat;

/**
 * @author kkinnebrew
 */
public class NotFoundException extends Exception {

  public NotFoundException(Type type, String id) {
    this(MessageFormat.format("{0} not found with identifier: {1}", type.toString(), id));
  }

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException() {}

}
