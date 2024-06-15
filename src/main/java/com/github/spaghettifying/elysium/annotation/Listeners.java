package com.github.spaghettifying.elysium.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Listeners {

    Listen[] value();

}
