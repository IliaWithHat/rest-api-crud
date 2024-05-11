package org.ilia.restapicrud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.ilia.restapicrud.dto.BirthDateRange;
import org.ilia.restapicrud.dto.UserDto;
import org.ilia.restapicrud.enums.UpdateType;
import org.ilia.restapicrud.service.UserService;
import org.ilia.restapicrud.validation.ValidateObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ValidateObject validateObject;

    @MockBean
    private UserService userService;

    private final UserDto user = new UserDto(1, "ilia@gmail.com", "Ilia", "Rozhko", LocalDate.of(2000, 1, 1), null, null);

    private final List<UserDto> allUsers = List.of(user);

    @Test
    void findAll() throws Exception {
        given(userService.findAll()).willReturn(allUsers);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())));
    }

    @Test
    void findByBirthDateRange() throws Exception {
        BirthDateRange birthDateRange = new BirthDateRange(LocalDate.of(2000, 1, 1), LocalDate.of(2001, 1, 1));
        given(userService.findByBirthDateRange(birthDateRange)).willReturn(allUsers);

        mockMvc.perform(get("/api/v1/users?from=2000-01-01&to=2001-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())));
    }

    @Test
    void notFoundByBirthDateRange() throws Exception {
        BirthDateRange birthDateRange = new BirthDateRange(LocalDate.of(2005, 1, 1), LocalDate.of(2006, 1, 1));
        given(userService.findByBirthDateRange(birthDateRange)).willReturn(List.of());

        mockMvc.perform(get("/api/v1/users?from=2005-01-01&to=2006-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void findById() throws Exception {
        given(userService.findById(1)).willReturn(Optional.of(user));

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    void notFoundById() throws Exception {
        given(userService.findById(2)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser() throws Exception {
        given(userService.create(user)).willReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    void fullUpdate() throws Exception {
        given(userService.update(eq(1), any(UserDto.class), eq(UpdateType.FULL_UPDATE)))
                .willReturn(Optional.of(user));

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    void partialUpdate() throws Exception {
        given(userService.update(eq(1), any(UserDto.class), eq(UpdateType.PARTIAL_UPDATE)))
                .willReturn(Optional.of(user));

        mockMvc.perform(patch("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    void deleteUser() throws Exception {
        given(userService.delete(1)).willReturn(true);

        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = JsonMapper.builder()
                    .findAndAddModules()
                    .build();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}