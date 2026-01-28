package com.geosieben.gsbworkday;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link GsbWorkDayApplication}.
 */
@Generated
public class GsbWorkDayApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'gsbWorkDayApplication'.
   */
  public static BeanDefinition getGsbWorkDayApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(GsbWorkDayApplication.class);
    beanDefinition.setInstanceSupplier(GsbWorkDayApplication::new);
    return beanDefinition;
  }
}
