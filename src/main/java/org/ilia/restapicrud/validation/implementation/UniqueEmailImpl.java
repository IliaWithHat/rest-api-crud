package org.ilia.restapicrud.validation.implementation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.ilia.restapicrud.dto.UserDto;
import org.ilia.restapicrud.entity.User;
import org.ilia.restapicrud.repository.UserRepository;
import org.ilia.restapicrud.validation.annotation.UniqueEmail;

import java.util.Optional;

@RequiredArgsConstructor
public class UniqueEmailImpl implements ConstraintValidator<UniqueEmail, UserDto> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        Optional<User> user = userRepository.findUserByEmail(userDto.getEmail());
        return user.isEmpty() || user.get().getId().equals(userDto.getId());
    }
}
