package com.geosieben.gsbworkday.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.geosieben.gsbworkday.dto.SubscriptionsProjection;
import com.geosieben.gsbworkday.entity.Subscriptions;

public interface SubscriptionRepository extends JpaRepository<Subscriptions,Integer> {
@Query(value = 
    "SELECT " +
    "  s.subscriptionId AS id, " +
    "  s.subscriptionName AS name, " +
    "  s.subscriptionProvider AS provider, " +
    "  s.contactNumber AS phone, " +
    "  s.contactEmail AS email, " +
    "  s.subscriptionType AS subscriptionType, " +
    "  h.subscriptionStartDate AS startDate, " +
    "  h.subscriptionExpiryDate AS expiryDate, " +
    "  h.invoicePath AS invoicePath, " +
    "  h.refId AS refId, " +
    "  CONCAT(e.firstname, ' ', e.lastname) AS renewedBy " +
    "FROM subscriptions s " +
    "LEFT JOIN subscriptionhistory h ON h.id = ( " +
    "    SELECT MAX(h2.id) " +
    "    FROM subscriptionhistory h2 " +
    "    WHERE h2.subscriptionId = s.subscriptionId " +
    ") " +
    "LEFT JOIN employee_basic_info e ON e.eid = h.renewedBy", 
    nativeQuery = true)
public  List<SubscriptionsProjection> getSubscriptions();
}
