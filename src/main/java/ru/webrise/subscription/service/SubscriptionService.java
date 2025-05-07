package ru.webrise.subscription.service;

import ru.webrise.subscription.dto.subscription.SubscriptionCountDto;
import ru.webrise.subscription.dto.subscription.SubscriptionDto;
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
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserSubscriptionMapper userSubscriptionMapper;

    public UserSubscriptionDto addSubscription(Long userId, SubscriptionRequestDto subscriptionRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Subscription subscription = new Subscription();
        subscription.setServiceName(subscriptionRequestDto.getServiceName());

        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setUser(user);
        userSubscription.setSubscription(subscription);
        userSubscription.setStartDate(subscriptionRequestDto.getStartDate());
        userSubscription.setEndDate(subscriptionRequestDto.getEndDate());

        UserSubscription savedUserSubscription = userSubscriptionRepository.save(userSubscription);

        return userSubscriptionMapper.userSubscriptionToUserSubscriptionDto(savedUserSubscription);
    }

    public List<UserSubscriptionDto> getSubscriptionsByUserId(Long userId) {
        List<UserSubscription> userSubscriptions = userSubscriptionRepository.findByUser_Id(userId);
        Stream<UserSubscriptionDto> stream = userSubscriptions.stream()
                .map(userSubscriptionMapper::userSubscriptionToUserSubscriptionDto);
        return stream.toList();
    }

    public void deleteSubscription(Long userId, Long subscriptionId) {
        UserSubscription userSubscription = userSubscriptionRepository.findByUser_IdAndSubscription_Id(userId, subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found with id: " + subscriptionId));

        userSubscriptionRepository.delete(userSubscription);
    }

    public List<SubscriptionCountDto> getTopSubscriptions() {
        List<Object[]> topSubscriptions = subscriptionRepository.findTopSubscriptions();
        List<SubscriptionCountDto> list = new ArrayList<>();
        for (Object[] topSubscription : topSubscriptions) {
            SubscriptionCountDto dto = new SubscriptionCountDto();
            Subscription subscription = subscriptionRepository.findById(((Number) topSubscription[0]).longValue())
                    .orElseThrow(() -> new EntityNotFoundException("Subscription not found with id: " + topSubscription[0]));
            SubscriptionDto subscriptionDto = subscriptionMapper.subscriptionToSubscriptionDto(subscription);
            dto.setSubscription(subscriptionDto);
            dto.setCount(((Number) topSubscription[1]).longValue());
            list.add(dto);
        }
        return list;
    }
}
