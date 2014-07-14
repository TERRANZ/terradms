package ru.terra.dms.desktop.core.annotations;

import java.lang.annotation.*;

/**
 * Date: 26.05.14
 * Time: 15:41
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ViewPartWindow {
    String name() default "";
    String fxml() default "";

}
