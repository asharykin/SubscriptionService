package ru.webrise.subscription.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.webrise.subscription.dto.user.UserRequestDto;
import ru.webrise.subscription.dto.user.UserResponseDto;
import ru.webrise.subscription.mapper.UserMapper;
import ru.webrise.subscription.model.User;
import ru.webrise.subscription.repository.UserRepository;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " does not exist"));
        return userMapper.userToUserResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("Found {} users", users.size());

        Stream<UserResponseDto> dtoStream = users.stream().map(userMapper::userToUserResponseDto);
        return dtoStream.toList();
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        checkUsernameAndEmail(requestDto);

        User user = userMapper.userRequestDtoToUser(requestDto);
        userRepository.save(user);
        log.info("User created successfully with id {}", user.getId());

        return userMapper.userToUserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " does not exist"));
        checkUsernameAndEmail(requestDto);

        userMapper.updateUserFromUserRequestDto(requestDto, user);
        userRepository.save(user);
        log.info("User with id {} updated successfully", id);

        return userMapper.userToUserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User with id {} deleted successfully", id);
        }
        throw new EntityNotFoundException("User with id " + id + " does not exist");
    }

    private void checkUsernameAndEmail(UserRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new EntityExistsException("User with username '" + requestDto.getUsername() + "' already exists");
        }
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new EntityExistsException("User with email '" + requestDto.getEmail() + "' already exists");
        }
    }
}


