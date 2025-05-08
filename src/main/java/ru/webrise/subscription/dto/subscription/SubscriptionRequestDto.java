package ru.webrise.subscription.dto.subscription;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SubscriptionRequestDto {
    private Long subscriptionId;
    private LocalDate startDate;
    private LocalDate endDate;
}

