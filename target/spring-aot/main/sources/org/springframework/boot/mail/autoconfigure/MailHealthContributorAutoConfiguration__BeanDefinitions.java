package org.springframework.boot.mail.autoconfigure;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.health.contributor.HealthContributor;

/**
 * Bean definitions for {@link MailHealthContributorAutoConfiguration}.
 */
@Generated
public class MailHealthContributorAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'mailHealthContributorAutoConfiguration'.
   */
  public static BeanDefinition getMailHealthContributorAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(MailHealthContributorAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(MailHealthContributorAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'mailHealthContributor'.
   */
  private static BeanInstanceSupplier<HealthContributor> getMailHealthContributorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<HealthContributor>forFactoryMethod(MailHealthContributorAutoConfiguration.class, "mailHealthContributor", ConfigurableListableBeanFactory.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.mail.autoconfigure.MailHealthContributorAutoConfiguration", MailHealthContributorAutoConfiguration.class).mailHealthContributor(args.get(0)));
  }

  /**
   * Get the bean definition for 'mailHealthContributor'.
   */
  public static BeanDefinition getMailHealthContributorBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(HealthContributor.class);
    beanDefinition.setFactoryBeanName("org.springframework.boot.mail.autoconfigure.MailHealthContributorAutoConfiguration");
    beanDefinition.setInstanceSupplier(getMailHealthContributorInstanceSupplier());
    return beanDefinition;
  }
}
