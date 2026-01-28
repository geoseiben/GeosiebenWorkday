package com.geosieben.gsbworkday.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link ItTicketService}.
 */
@Generated
public class ItTicketService__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static ItTicketService apply(RegisteredBean registeredBean, ItTicketService instance) {
    AutowiredFieldValueResolver.forRequiredField("itTicketRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("itmail").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("itname").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("basicInfoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("httpSession").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("emailService").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
