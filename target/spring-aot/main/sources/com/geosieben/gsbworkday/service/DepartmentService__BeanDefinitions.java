package com.geosieben.gsbworkday.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link DepartmentService}.
 */
@Generated
public class DepartmentService__BeanDefinitions {
  /**
   * Get the bean definition for 'departmentService'.
   */
  public static BeanDefinition getDepartmentServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(DepartmentService.class);
    InstanceSupplier<DepartmentService> instanceSupplier = InstanceSupplier.using(DepartmentService::new);
    instanceSupplier = instanceSupplier.andThen(DepartmentService__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
