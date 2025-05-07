package ru.webrise.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.webrise.subscription.model.UserSubscription;

import java.util.List;
import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    List<UserSubscription> findByUser_Id(Long userId);

    Optional<UserSubscription> findByUser_IdAndSubscription_Id(Long userId, Long subscriptionId);
}

