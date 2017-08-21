package com.eyelinecom.whoisd.sads2.sender.services;

import javax.inject.Qualifier;
import java.lang.annotation.*;

/**
 * author: Artem Voronov
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface Service {
}
