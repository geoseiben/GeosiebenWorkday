package com.geosieben.gsbworkday.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link LeaveServices}.
 */
@Generated
public class LeaveServices__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static LeaveServices apply(RegisteredBean registeredBean, LeaveServices instance) {
    AutowiredFieldValueResolver.forRequiredField("leaveBalanceRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("bankInfoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("leaveRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("basicInfoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("httpSession").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("emailService").resolveAndSet(registeredBean, instance);
    instance.toname = AutowiredFieldValueResolver.forRequiredField("toname").resolve(registeredBean);
    instance.tomail = AutowiredFieldValueResolver.forRequiredField("tomail").resolve(registeredBean);
    return instance;
  }
}
