package com.geosieben.gsbworkday.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link LeaveServices}.
 */
@Generated
public class LeaveServices__BeanDefinitions {
  /**
   * Get the bean definition for 'leaveServices'.
   */
  public static BeanDefinition getLeaveServicesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(LeaveServices.class);
    InstanceSupplier<LeaveServices> instanceSupplier = InstanceSupplier.using(LeaveServices::new);
    instanceSupplier = instanceSupplier.andThen(LeaveServices__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
