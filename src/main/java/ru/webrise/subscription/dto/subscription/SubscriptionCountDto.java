package ru.webrise.subscription.dto.subscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionCountDto {
    private SubscriptionDto subscription;
    private long count;
}

