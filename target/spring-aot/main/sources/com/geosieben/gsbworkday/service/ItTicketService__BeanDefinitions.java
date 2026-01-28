package com.geosieben.gsbworkday.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ItTicketService}.
 */
@Generated
public class ItTicketService__BeanDefinitions {
  /**
   * Get the bean definition for 'itTicketService'.
   */
  public static BeanDefinition getItTicketServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ItTicketService.class);
    InstanceSupplier<ItTicketService> instanceSupplier = InstanceSupplier.using(ItTicketService::new);
    instanceSupplier = instanceSupplier.andThen(ItTicketService__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
