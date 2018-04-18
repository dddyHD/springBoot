package cn.suancloud.springBoot.exception;

/**
 * Created by admin on 2018/4/18.
 */
public class FormException extends Exception {
  public FormException(String message){
    super(message);
  }
  public FormException() {
  }
}
