package com.geosieben.gsbworkday.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link UserServiceImplimentation}.
 */
@Generated
public class UserServiceImplimentation__BeanDefinitions {
  /**
   * Get the bean definition for 'userServiceImplimentation'.
   */
  public static BeanDefinition getUserServiceImplimentationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(UserServiceImplimentation.class);
    InstanceSupplier<UserServiceImplimentation> instanceSupplier = InstanceSupplier.using(UserServiceImplimentation::new);
    instanceSupplier = instanceSupplier.andThen(UserServiceImplimentation__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
