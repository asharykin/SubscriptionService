package ru.webrise.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.webrise.subscription.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}

