package ru.webrise.subscription.dto.subscription;

import lombok.Getter;
import lombok.Setter;
import ru.webrise.subscription.dto.user.UserResponseDto;

import java.time.LocalDate;

@Getter
@Setter
public class UserSubscriptionDto {
    private Long id;
    private UserResponseDto user;
    private SubscriptionResponseDto subscription;
    private LocalDate startDate;
    private LocalDate endDate;
}

