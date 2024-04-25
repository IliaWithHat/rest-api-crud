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

    @NotNull(message = "Enter email")
    @Email(message = "Enter correct email")
    String email;

    @NotBlank(message = "Enter first name")
    String firstName;

    @NotBlank(message = "Enter last name")
    String lastName;

    @NotNull(message = "Enter birth date")
    @Past(message = "Birth date cannot be past")
    @MinimumAge
    LocalDate birthDate;

    String address;

    String phoneNumber;
}
