package com.geosieben.gsbworkday.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CustomUserDetails}.
 */
@Generated
public class CustomUserDetails__BeanDefinitions {
  /**
   * Get the bean definition for 'customUserDetails'.
   */
  public static BeanDefinition getCustomUserDetailsBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CustomUserDetails.class);
    InstanceSupplier<CustomUserDetails> instanceSupplier = InstanceSupplier.using(CustomUserDetails::new);
    instanceSupplier = instanceSupplier.andThen(CustomUserDetails__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
