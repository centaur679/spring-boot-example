package com.livk.autoconfigure.qrcode.annotation;

import com.livk.autoconfigure.qrcode.enums.PicType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * QRCode
 * </p>
 *
 * @author livk
 * @date 2022/11/4
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QRCode {

    int width() default 400;

    int height() default 400;

    PicType type() default PicType.JPG;
}
