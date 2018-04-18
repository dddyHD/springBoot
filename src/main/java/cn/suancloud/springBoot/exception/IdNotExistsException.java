package cn.suancloud.springBoot.exception;

/**
 * Created by admin on 2018/4/18.
 */
public class IdNotExistsException extends Exception {
  public IdNotExistsException() {
  }

  public IdNotExistsException(String message) {
    super(message);
  }
}
