package cn.suancloud.springBoot.exception;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import cn.suancloud.springBoot.util.ResponseData;


/**
 * Created by teruo on 2018/1/31.
 *  捕捉全局异常
 */
@RestControllerAdvice
public class ExceptionAdvice {
  @ExceptionHandler(Exception.class)
  public ResponseData defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    ResponseData data;
    if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
      data = ResponseData.notFound(e.getMessage());
    } else if (e instanceof HttpMessageNotReadableException) {
      data = ResponseData.formValidError("请求体为空");
    } else if (e instanceof FormException) {
      data = ResponseData.formValidError(e.getMessage());
    } else if (e instanceof IdNotExistsException) {
      data = ResponseData.idNotExistsError();
    } else {
      data = ResponseData.serverInternalError(e.getMessage());
    }
    return data;
  }
}
