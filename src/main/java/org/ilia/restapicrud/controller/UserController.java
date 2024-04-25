package org.ilia.restapicrud.controller;

import lombok.RequiredArgsConstructor;
import org.ilia.restapicrud.dto.BirthDateRange;
import org.ilia.restapicrud.dto.UserDto;
import org.ilia.restapicrud.service.UserService;
import org.ilia.restapicrud.validation.ValidateObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.ilia.restapicrud.enums.UpdateType.FULL_UPDATE;
import static org.ilia.restapicrud.enums.UpdateType.PARTIAL_UPDATE;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ValidateObject validateObject;

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/birthDateRange")
    public List<UserDto> findByBirthDateRange(BirthDateRange birthDateRange) {
        validateObject.validate(birthDateRange);
        return userService.findByBirthDateRange(birthDateRange);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Integer id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        validateObject.validate(userDto);
        return userService.create(userDto);
    }

    @PutMapping("/{id}")
    public UserDto fullUpdate(@PathVariable Integer id, @RequestBody UserDto userDto) {
        validateObject.validate(userDto);
        return userService.update(id, userDto, FULL_UPDATE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public UserDto partialUpdate(@PathVariable Integer id, @RequestBody UserDto userDto) {
        return userService.update(id, userDto, PARTIAL_UPDATE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return userService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
