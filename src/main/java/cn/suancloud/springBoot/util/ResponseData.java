package cn.suancloud.springBoot.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by teruo on 2017/8/11.
 * restful response object.
 */
public class ResponseData {
  private final String developerMessage;
  private final String message;
  private final int status;
  private final Map<String, Object> data = new HashMap<String, Object>();

  public String getDeveloperMessage() {
    return developerMessage;
  }

  public String getMessage() {
    return message;
  }

  public int getStatus() {
    return status;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public ResponseData putDataValue(String key, Object value) {
    data.put(key, value);
    return this;
  }

  public ResponseData() {
    developerMessage = null;
    message = null;
    status = 200;
  }

  private ResponseData(int status, String message) {
    this(null, status, message);
  }

  public ResponseData(String developerMessage, int status, String message) {
    this.developerMessage = developerMessage;
    this.message = message;
    this.status = status;
  }

  public static ResponseData ok() {
    return new ResponseData(200, "success");
  }

  public static ResponseData notFound() {
    return new ResponseData(404, "Not Found");
  }

  public static ResponseData notFound(String message) {
    return new ResponseData(404, message);
  }

  public static ResponseData badRequest() {
    return new ResponseData(400, "Bad Request");
  }

  public static ResponseData forbidden() {
    return new ResponseData(403, "Forbidden");
  }

  public static ResponseData unauthorized() {
    return new ResponseData(401, "unauthorized");
  }

  public static ResponseData serverInternalError(String developerMessage) {
    return new ResponseData(developerMessage, 500, "Server Internal Error");
  }

  //系统错误信息对照表
  public static ResponseData formValidError(String message) {
    return new ResponseData(null, 601, message);
  }
  public static ResponseData idNotExistsError() {
    return new ResponseData(602, "id不存在");
  }

  public static ResponseData usernameAlreadyExistsError(){
    return new ResponseData(603,"该用户名已经存在");
  }



}
