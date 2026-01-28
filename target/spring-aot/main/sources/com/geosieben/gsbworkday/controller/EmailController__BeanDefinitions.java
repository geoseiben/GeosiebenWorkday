package com.geosieben.gsbworkday.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link EmailController}.
 */
@Generated
public class EmailController__BeanDefinitions {
  /**
   * Get the bean definition for 'emailController'.
   */
  public static BeanDefinition getEmailControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(EmailController.class);
    InstanceSupplier<EmailController> instanceSupplier = InstanceSupplier.using(EmailController::new);
    instanceSupplier = instanceSupplier.andThen(EmailController__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
