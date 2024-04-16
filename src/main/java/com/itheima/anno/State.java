package com.itheima.anno;

import com.itheima.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(
        validatedBy = {StateValidation.class}
)
@Target(FIELD)
@Retention(RUNTIME)
public @interface State {
    //提供校验失败提示信息
    String message() default "{state的参数的值只能是已发布或草稿}";
    //指定分组
    Class<?>[] groups() default {};
    //负载
    Class<? extends Payload>[] payload() default {};
}
