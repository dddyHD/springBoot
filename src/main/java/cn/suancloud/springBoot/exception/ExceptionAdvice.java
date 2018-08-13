package cn.suancloud.springBoot.exception;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.suancloud.springBoot.util.ResponseData;
import io.fabric8.kubernetes.api.model.Status;
import io.fabric8.kubernetes.client.KubernetesClientException;


/**
 * Created by teruo on 2018/1/31. 捕捉全局异常
 */
@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(KubernetesClientException.class)
  public Status ErrorHandler(HttpServletRequest req, HttpServletResponse res,
                             KubernetesClientException e) {
    res.setStatus(e.getStatus().getCode());
    return e.getStatus();
  }


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
