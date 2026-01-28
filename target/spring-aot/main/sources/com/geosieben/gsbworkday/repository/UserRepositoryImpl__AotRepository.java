package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link UserRepository}.
 */
@Generated
public class UserRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public UserRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link UserRepository#findByEmployeeBasicInfo_EID(java.lang.String)}.
   */
  public User findByEmployeeBasicInfo_EID(String eid) {
    String queryString = "SELECT u FROM User u WHERE u.employeeBasicInfo.EID = :eid";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("eid", eid);

    return (User) convertOne(query.getSingleResultOrNull(), false, User.class);
  }

  /**
   * AOT generated implementation of {@link UserRepository#findUserByUsername(java.lang.String)}.
   */
  public User findUserByUsername(String username) {
    String queryString = "SELECT u FROM User u WHERE u.username = :username";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("username", username);

    return (User) convertOne(query.getSingleResultOrNull(), false, User.class);
  }
}
