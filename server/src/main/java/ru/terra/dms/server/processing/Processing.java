package ru.terra.dms.server.processing;

import java.lang.annotation.*;

/**
 * Date: 05.06.14
 * Time: 17:05
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Processing {
    String value();
}
