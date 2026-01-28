package com.geosieben.gsbworkday.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link EmployeeService}.
 */
@Generated
public class EmployeeService__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static EmployeeService apply(RegisteredBean registeredBean, EmployeeService instance) {
    AutowiredFieldValueResolver.forRequiredField("basicInfoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("joiningInfoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("designationInfoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("bankInfoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("educationInfoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("statutoryInfoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("emergencyInfoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("userRepository").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
