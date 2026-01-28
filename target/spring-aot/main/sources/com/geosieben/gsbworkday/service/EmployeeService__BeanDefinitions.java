package com.geosieben.gsbworkday.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link EmployeeService}.
 */
@Generated
public class EmployeeService__BeanDefinitions {
  /**
   * Get the bean definition for 'employeeService'.
   */
  public static BeanDefinition getEmployeeServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(EmployeeService.class);
    InstanceSupplier<EmployeeService> instanceSupplier = InstanceSupplier.using(EmployeeService::new);
    instanceSupplier = instanceSupplier.andThen(EmployeeService__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
