package com.github.spaghettifying.elysium.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Listeners.class)
public @interface Listen {

    Class<?> value();

}
