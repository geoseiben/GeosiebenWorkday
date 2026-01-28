package com.geosieben.gsbworkday.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link CustomUserDetails}.
 */
@Generated
public class CustomUserDetails__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static CustomUserDetails apply(RegisteredBean registeredBean, CustomUserDetails instance) {
    AutowiredFieldValueResolver.forRequiredField("userRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("passwordEncoder").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("httpSession").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
