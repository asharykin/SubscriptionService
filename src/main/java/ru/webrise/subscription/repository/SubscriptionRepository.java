package ru.webrise.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.webrise.subscription.model.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(value = "SELECT s.id, COUNT(us.user_id) AS count " +
            "FROM subscriptions s " +
            "JOIN users_subscriptions us ON s.id = us.subscription_id " +
            "GROUP BY s.id " +
            "ORDER BY count DESC " +
            "LIMIT 3", nativeQuery = true)
    List<Object[]> findTopSubscriptions();
}
