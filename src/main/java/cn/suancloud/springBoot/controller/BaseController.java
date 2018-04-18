package cn.suancloud.springBoot.controller;

import org.springframework.validation.BindingResult;

import java.io.Serializable;
import java.util.Arrays;

import cn.suancloud.springBoot.exception.FormException;
import cn.suancloud.springBoot.exception.IdNotExistsException;
import cn.suancloud.springBoot.service.BaseService;
import cn.suancloud.springBoot.util.Extractor;

/**
 * Created by admin on 2018/4/18.
 */
public class BaseController {

  protected void hasErrors(BindingResult result) throws FormException {
    if (result.hasErrors()) {
      throw new FormException(Arrays.toString(Extractor.extractErrorMsg(result)));
    }
  }

  protected void isExists(BaseService service, Serializable id) throws IdNotExistsException {
    if (!service.exists(id)) {
      throw new IdNotExistsException();
    }
  }
}
