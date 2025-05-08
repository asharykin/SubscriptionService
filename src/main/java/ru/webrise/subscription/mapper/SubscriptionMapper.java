package ru.webrise.subscription.mapper;

import org.mapstruct.Mapper;
import ru.webrise.subscription.dto.subscription.SubscriptionResponseDto;
import ru.webrise.subscription.model.Subscription;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface SubscriptionMapper {

    SubscriptionResponseDto subscriptionToSubscriptionResponseDto(Subscription subscription);
}

