package ru.webrise.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.webrise.subscription.model.UserSubscription;

import java.util.List;
import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    List<UserSubscription> findByUserId(Long userId);

    Optional<UserSubscription> findByUserIdAndSubscriptionId(Long userId, Long subscriptionId);
}

