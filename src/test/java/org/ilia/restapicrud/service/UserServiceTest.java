package org.ilia.restapicrud.service;

import org.ilia.restapicrud.dto.BirthDateRange;
import org.ilia.restapicrud.dto.UserDto;
import org.ilia.restapicrud.entity.User;
import org.ilia.restapicrud.enums.UpdateType;
import org.ilia.restapicrud.mapper.UserMapper;
import org.ilia.restapicrud.repository.UserRepository;
import org.ilia.restapicrud.validation.ValidateObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ValidateObject validateObject;

    @InjectMocks
    private UserService userService;

    private final UserDto userDto = new UserDto(1, "ilia@gmail.com", "Ilia", "Rozhko", LocalDate.of(2000, 1, 1), null, null);
    private final User user = new User(1, "ilia@gmail.com", "Ilia", "Rozhko", LocalDate.of(2000, 1, 1), null, null);

    @Test
    void findAll() {
        given(userRepository.findAll()).willReturn(List.of(user));
        given(userMapper.toUserDto(user)).willReturn(userDto);

        List<UserDto> result = userService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(userDto);
    }

    @Test
    void findByBirthDateRange() {
        LocalDate fromDate = LocalDate.of(2000, 1, 1);
        LocalDate toDate = LocalDate.of(2001, 1, 1);
        BirthDateRange birthDateRange = new BirthDateRange(fromDate, toDate);

        given(userRepository.findByBirthDateAfterAndBirthDateBefore(fromDate, toDate)).willReturn(List.of(user));
        given(userMapper.toUserDto(user)).willReturn(userDto);

        List<UserDto> result = userService.findByBirthDateRange(birthDateRange);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(userDto);
    }

    @Test
    void findById() {
        given(userRepository.findById(1)).willReturn(Optional.of(user));
        given(userMapper.toUserDto(user)).willReturn(userDto);

        Optional<UserDto> result = userService.findById(1);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(userDto);
    }

    @Test
    void create() {
        given(userMapper.toUser(userDto)).willReturn(user);
        given(userRepository.save(user)).willReturn(user);
        given(userMapper.toUserDto(user)).willReturn(userDto);

        UserDto result = userService.create(userDto);

        assertThat(result).isEqualTo(userDto);
    }

    @Test
    void fullUpdate() {
        given(userRepository.findById(1)).willReturn(Optional.of(user));
        given(userMapper.copyUserDtoToUser(userDto, user)).willReturn(user);
        given(userRepository.save(user)).willReturn(user);
        given(userMapper.toUserDto(user)).willReturn(userDto);

        Optional<UserDto> result = userService.update(1, userDto, UpdateType.FULL_UPDATE);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(userDto);
    }

    @Test
    void partialUpdate() {
        UserDto partialUserDto = new UserDto(1, null, "NotIlia", null, null, null, null);
        User updatedUser = new User(1, "ilia@gmail.com", "NotIlia", "Rozhko", LocalDate.of(2000, 1, 1), null, null);
        UserDto updatedUserDto = new UserDto(1, "ilia@gmail.com", "NotIlia", "Rozhko", LocalDate.of(2000, 1, 1), null, null);

        given(userRepository.findById(1)).willReturn(Optional.of(user));
        given(userMapper.copyUserDtoToUserIgnoreNull(partialUserDto, user)).willReturn(updatedUser);
        given(userRepository.save(updatedUser)).willReturn(updatedUser);
        given(userMapper.toUserDto(updatedUser)).willReturn(updatedUserDto);

        Optional<UserDto> result = userService.update(1, partialUserDto, UpdateType.PARTIAL_UPDATE);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(updatedUserDto);
    }

    @Test
    void delete() {
        given(userRepository.findById(1)).willReturn(Optional.of(user));

        boolean result = userService.delete(1);

        assertThat(result).isTrue();
    }
}