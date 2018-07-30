package cn.suancloud.springBoot.util.validutil;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;



@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy =HttpMethodContainValidator.class)
public @interface HttpMethodContain {
  /**
   * 添加value属性，可以作为校验时的条件,若不需要，可去掉此处定义
   */
  int value() default 0;
  Class<?>[] groups() default {};
  String message() default "提交的方法不属于http方法类型！";
  Class<? extends Payload>[] payload() default {};
}
