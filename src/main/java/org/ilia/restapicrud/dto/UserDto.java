package org.ilia.restapicrud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Value;
import org.ilia.restapicrud.validation.annotation.MinimumAge;
import org.ilia.restapicrud.validation.annotation.UniqueEmail;

import java.time.LocalDate;

@Value
@UniqueEmail
public class UserDto {

    Integer id;

    @NotNull
    @Email
    String email;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotNull
    @Past
    @MinimumAge
    LocalDate birthDate;

    String address;

    String phoneNumber;
}
