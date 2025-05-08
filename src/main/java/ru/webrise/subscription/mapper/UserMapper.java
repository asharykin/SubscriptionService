package ru.webrise.subscription.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.webrise.subscription.dto.user.UserRequestDto;
import ru.webrise.subscription.dto.user.UserResponseDto;
import ru.webrise.subscription.model.User;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Named("userToUserResponseDto")
    UserResponseDto userToUserResponseDto(User user);

    User userRequestDtoToUser(UserRequestDto userRequestDto);

    void updateUserFromUserRequestDto(UserRequestDto userRequestDto, @MappingTarget User user);
}
