package ru.webrise.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.webrise.subscription.model.UserSubscription;

import java.util.List;
import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    @Query("SELECT us FROM UserSubscription us WHERE us.user.id = :userId")
    List<UserSubscription> findByUserId(Long userId);

    @Query("SELECT us FROM UserSubscription us WHERE us.user.id = :userId AND us.subscription.id = :subscriptionId")
    Optional<UserSubscription> findByUserIdAndSubscriptionId(Long userId, Long subscriptionId);
}

