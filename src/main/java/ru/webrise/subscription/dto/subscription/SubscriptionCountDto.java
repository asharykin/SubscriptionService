package ru.webrise.subscription.dto.subscription;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionCountDto {
    private SubscriptionResponseDto subscription;
    private long count;
}

