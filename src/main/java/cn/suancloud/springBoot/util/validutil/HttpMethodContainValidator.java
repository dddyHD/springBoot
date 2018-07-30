package cn.suancloud.springBoot.util.validutil;

import org.springframework.util.StringUtils;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HttpMethodContainValidator implements ConstraintValidator<HttpMethodContain, String> {
  private int value;
  private String[] array = {"ALL", "GET", "POST", "PUT", "PATCH"};

  @Override
  public void initialize(HttpMethodContain constraintAnnotation) {
    this.value = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isEmpty(value))
      return false;
    else
      return Arrays.asList(array).contains(value);
  }
}
