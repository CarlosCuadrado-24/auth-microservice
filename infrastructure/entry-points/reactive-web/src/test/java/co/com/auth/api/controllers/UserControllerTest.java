package co.com.auth.api.controllers;

import co.com.auth.api.dtos.RequestRoleDTO;
import co.com.auth.api.dtos.RequestUserDTO;
import co.com.auth.api.dtos.ResponseUserDTO;
import co.com.auth.model.role.Role;
import co.com.auth.model.user.User;
import co.com.auth.usecase.role.GetByIdUseCase;
import co.com.auth.usecase.user.SaveUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private WebTestClient client;
    private SaveUserUseCase userUseCase;
    private GetByIdUseCase getByIdUseCaseRole;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        userUseCase         = Mockito.mock(SaveUserUseCase.class);
        getByIdUseCaseRole  = Mockito.mock(GetByIdUseCase.class);
        mapper              = Mockito.mock(ObjectMapper.class);

        UserController controller = new UserController(userUseCase, getByIdUseCaseRole, mapper);

        client = WebTestClient.bindToController(controller)
                .build();
    }

    @Test
    void saveUser() {

        var req = new RequestUserDTO(
                "Carlos", "Cuadrado",
                LocalDate.parse("1999-06-15"),
                "3001234567",
                "carlos.cuadrado@example.com",
                2_500_000L,
                7L // idRole
        );


        var mappedUser = new User(
                "Carlos", "Cuadrado",
                LocalDate.parse("1999-06-15"),
                "3001234567",
                "carlos.cuadrado@example.com",
                2_500_000L,
                null
        );

        var role = new Role("admin", "todos los permisos");

        var savedUser = new User(
                "Carlos", "Cuadrado",
                LocalDate.parse("1999-06-15"),
                "3001234567",
                "carlos.cuadrado@example.com",
                2_500_000L,
                role
        );

        var resp = new ResponseUserDTO(
                123L,
                "Carlos",
                "Cuadrado",
                LocalDate.parse("1999-06-15"),
                "3001234567",
                "carlos.cuadrado@example.com",
                2_500_000L,
                new RequestRoleDTO("admin", "todos los permisos")
        );


        when(mapper.map(any(RequestUserDTO.class), eq(User.class)))
                .thenReturn(mappedUser);


        when(getByIdUseCaseRole.getRoleById(eq(7L)))
                .thenReturn(Mono.just(role));


        when(userUseCase.saveUser(any(User.class)))
                .thenReturn(Mono.just(savedUser));


        when(mapper.map(eq(savedUser), eq(ResponseUserDTO.class)))
                .thenReturn(resp);

        client.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseUserDTO.class)
                .isEqualTo(resp);
    }
}