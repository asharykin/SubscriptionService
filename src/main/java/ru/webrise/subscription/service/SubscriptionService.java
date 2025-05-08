package ru.webrise.subscription.service;

import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import ru.webrise.subscription.dto.subscription.SubscriptionCountDto;
import ru.webrise.subscription.dto.subscription.SubscriptionResponseDto;
import ru.webrise.subscription.dto.subscription.SubscriptionRequestDto;
import ru.webrise.subscription.dto.subscription.UserSubscriptionDto;
import ru.webrise.subscription.mapper.SubscriptionMapper;
import ru.webrise.subscription.mapper.UserSubscriptionMapper;
import ru.webrise.subscription.model.Subscription;
import ru.webrise.subscription.model.User;
import ru.webrise.subscription.model.UserSubscription;
import ru.webrise.subscription.repository.SubscriptionRepository;
import ru.webrise.subscription.repository.UserRepository;
import ru.webrise.subscription.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    private final SubscriptionMapper subscriptionMapper;
    private final UserSubscriptionMapper userSubscriptionMapper;

    public List<UserSubscriptionDto> getSubscriptionsByUserId(Long userId) {
        List<UserSubscription> userSubscriptions = userSubscriptionRepository.findByUserId(userId);
        log.info("Found {} subscriptions for user with id {}", userSubscriptions.size(), userId);

        Stream<UserSubscriptionDto> dtoStream = userSubscriptions.stream()
                .map(userSubscriptionMapper::userSubscriptionToUserSubscriptionDto);
        return dtoStream.toList();
    }

    public List<SubscriptionCountDto> getTopSubscriptions() {
        List<Object[]> topSubscriptions = subscriptionRepository.findTopSubscriptions();
        List<SubscriptionCountDto> subscriptionCountDtoList = new ArrayList<>();

        for (Object[] topSubscription : topSubscriptions) {
            long subId = ((Number) topSubscription[0]).longValue();
            long count = ((Number) topSubscription[1]).longValue();

            Subscription subscription = subscriptionRepository.findById(subId).get();
            SubscriptionResponseDto subscriptionResponseDto = subscriptionMapper.subscriptionToSubscriptionResponseDto(subscription);

            SubscriptionCountDto responseDto = new SubscriptionCountDto();
            responseDto.setSubscription(subscriptionResponseDto);
            responseDto.setCount(count);
            subscriptionCountDtoList.add(responseDto);
        }
        return subscriptionCountDtoList;
    }

    @Transactional
    public UserSubscriptionDto addSubscription(Long userId, SubscriptionRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " does not exist"));

        long subId = requestDto.getSubscriptionId();
        Subscription subscription = subscriptionRepository.findById(subId)
                .orElseThrow(() -> new EntityNotFoundException("Service with id " + subId + " does not exist"));

        if (userSubscriptionRepository.findByUserIdAndSubscriptionId(userId, subId).isEmpty()) {
            UserSubscription userSubscription = new UserSubscription();
            userSubscription.setUser(user);
            userSubscription.setSubscription(subscription);
            userSubscription.setStartDate(requestDto.getStartDate());
            userSubscription.setEndDate(requestDto.getEndDate());

            UserSubscription savedUserSubscription = userSubscriptionRepository.save(userSubscription);
            log.info("User with id {} subscribed successfully to {}", userId, subscription.getServiceName());

            return userSubscriptionMapper.userSubscriptionToUserSubscriptionDto(savedUserSubscription);
        }
        throw new EntityExistsException("User with id " + userId + " is already subscribed to " + subscription.getServiceName());
    }

    @Transactional
    public void deleteSubscription(Long userId, Long subId) {
        UserSubscription userSubscription = userSubscriptionRepository.findByUserIdAndSubscriptionId(userId, subId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " is not subscribed to service with id " + subId));

        userSubscriptionRepository.delete(userSubscription);
        log.info("User with id {} unsubscribed successfully from {}", userId, userSubscription.getSubscription().getServiceName());
    }
}
