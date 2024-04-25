package org.ilia.restapicrud.service;

import lombok.RequiredArgsConstructor;
import org.ilia.restapicrud.dto.BirthDateRange;
import org.ilia.restapicrud.dto.UserDto;
import org.ilia.restapicrud.entity.User;
import org.ilia.restapicrud.enums.UpdateType;
import org.ilia.restapicrud.mapper.UserMapper;
import org.ilia.restapicrud.repository.UserRepository;
import org.ilia.restapicrud.validation.ValidateObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.ilia.restapicrud.enums.UpdateType.FULL_UPDATE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ValidateObject validateObject;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public List<UserDto> findByBirthDateRange(BirthDateRange birthDateRange) {
        return userRepository.findByBirthDateAfterAndBirthDateBefore(birthDateRange.getFrom(), birthDateRange.getTo()).stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public Optional<UserDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto);
    }

    public UserDto create(UserDto userDto) {
        return Optional.of(userDto)
                .map(userMapper::toUser)
                .map(userRepository::save)
                .map(userMapper::toUserDto)
                .orElseThrow();
    }

    public Optional<UserDto> update(Integer id, UserDto userDto, UpdateType updateType) {
        return userRepository.findById(id)
                .map(user -> mergeUserDtoAndUser(userDto, user, updateType))
                .map(userRepository::save)
                .map(userMapper::toUserDto);
    }

    private User mergeUserDtoAndUser(UserDto userDto, User user, UpdateType updateType) {
        if (updateType == FULL_UPDATE) {
            return userMapper.copyUserDtoToUser(userDto, user);
        } else {
            User userForUpdate = userMapper.copyUserDtoToUserIgnoreNull(userDto, user);
            validateObject.validate(userMapper.toUserDto(userForUpdate));
            return userForUpdate;
        }
    }

    public boolean delete(Integer id) {
        Optional<User> maybeUser = userRepository.findById(id);
        if (maybeUser.isPresent()) {
            userRepository.delete(maybeUser.get());
            return true;
        } else {
            return false;
        }
    }
}
