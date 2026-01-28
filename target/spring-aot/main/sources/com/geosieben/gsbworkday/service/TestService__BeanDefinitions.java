package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.repository.TestDesgnRepo;
import com.geosieben.gsbworkday.repository.TestInfoRepo;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link TestService}.
 */
@Generated
public class TestService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'testService'.
   */
  private static BeanInstanceSupplier<TestService> getTestServiceInstanceSupplier() {
    return BeanInstanceSupplier.<TestService>forConstructor(TestInfoRepo.class, TestDesgnRepo.class)
            .withGenerator((registeredBean, args) -> new TestService(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'testService'.
   */
  public static BeanDefinition getTestServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(TestService.class);
    beanDefinition.setInstanceSupplier(getTestServiceInstanceSupplier());
    return beanDefinition;
  }
}
