package com.geosieben.gsbworkday.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link EmailController}.
 */
@Generated
public class EmailController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static EmailController apply(RegisteredBean registeredBean, EmailController instance) {
    AutowiredFieldValueResolver.forRequiredField("emailService").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
