package ru.webrise.subscription.controller;

import ru.webrise.subscription.dto.subscription.SubscriptionCountDto;
import ru.webrise.subscription.dto.subscription.SubscriptionRequestDto;
import ru.webrise.subscription.dto.subscription.UserSubscriptionDto;
import ru.webrise.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{userId}/subscriptions")
    public ResponseEntity<UserSubscriptionDto> addSubscription(@PathVariable Long userId, @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        UserSubscriptionDto addedSubscription = subscriptionService.addSubscription(userId, subscriptionRequestDto);
        return new ResponseEntity<>(addedSubscription, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/subscriptions")
    public ResponseEntity<List<UserSubscriptionDto>> getSubscriptions(@PathVariable Long userId) {
        List<UserSubscriptionDto> subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/subscriptions/{subId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long userId, @PathVariable Long subId) {
        subscriptionService.deleteSubscription(userId, subId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(" /subscriptions/top")
    public ResponseEntity<List<SubscriptionCountDto>> getTopSubscriptions() {
        List<SubscriptionCountDto> topSubscriptions = subscriptionService.getTopSubscriptions();
        return new ResponseEntity<>(topSubscriptions, HttpStatus.OK);
    }
}

