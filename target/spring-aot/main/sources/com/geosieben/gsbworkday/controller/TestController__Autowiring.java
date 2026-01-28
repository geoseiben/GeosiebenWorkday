package com.geosieben.gsbworkday.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link TestController}.
 */
@Generated
public class TestController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static TestController apply(RegisteredBean registeredBean, TestController instance) {
    AutowiredFieldValueResolver.forRequiredField("testService").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("httpSession").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("employeeService").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("leaveServices").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("itTicketService").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("departmentService").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
