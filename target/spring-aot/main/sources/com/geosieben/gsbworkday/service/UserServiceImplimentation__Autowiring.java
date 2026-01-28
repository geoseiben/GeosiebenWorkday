package com.geosieben.gsbworkday.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link UserServiceImplimentation}.
 */
@Generated
public class UserServiceImplimentation__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static UserServiceImplimentation apply(RegisteredBean registeredBean,
      UserServiceImplimentation instance) {
    AutowiredFieldValueResolver.forRequiredField("userRepository").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
