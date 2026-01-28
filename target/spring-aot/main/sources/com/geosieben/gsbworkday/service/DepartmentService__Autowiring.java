package com.geosieben.gsbworkday.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link DepartmentService}.
 */
@Generated
public class DepartmentService__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static DepartmentService apply(RegisteredBean registeredBean, DepartmentService instance) {
    AutowiredFieldValueResolver.forRequiredField("departmentRepository").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
