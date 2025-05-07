package ru.webrise.subscription.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.webrise.subscription.dto.subscription.UserSubscriptionDto;
import ru.webrise.subscription.model.UserSubscription;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, uses = {UserMapper.class, SubscriptionMapper.class})
public interface UserSubscriptionMapper {

    @Mapping(source = "user", target = "user", qualifiedByName = "userToUserResponseDto")
    @Mapping(source = "subscription", target = "subscription")
    UserSubscriptionDto userSubscriptionToUserSubscriptionDto(UserSubscription userSubscription);
}

