package ru.webrise.subscription.mapper;

import org.mapstruct.Mapper;
import ru.webrise.subscription.dto.subscription.SubscriptionDto;
import ru.webrise.subscription.model.Subscription;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface SubscriptionMapper {

    SubscriptionDto subscriptionToSubscriptionDto(Subscription subscription);
}

