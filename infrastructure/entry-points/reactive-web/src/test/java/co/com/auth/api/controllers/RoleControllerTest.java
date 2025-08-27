package co.com.auth.api.controllers;

import co.com.auth.api.dtos.RequestRoleDTO;
import co.com.auth.api.dtos.ResponseRoleDTO;
import co.com.auth.model.role.Role;
import co.com.auth.usecase.role.GetByIdUseCase;
import co.com.auth.usecase.role.SaveRoleUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


class RoleControllerTest {

    private WebTestClient client;
    private SaveRoleUseCase saveRoleUseCase;
    private GetByIdUseCase getByIdUseCase;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        saveRoleUseCase = Mockito.mock(SaveRoleUseCase.class);
        getByIdUseCase  = Mockito.mock(GetByIdUseCase.class);
        mapper          = Mockito.mock(ObjectMapper.class);

        RoleController controller = new RoleController(saveRoleUseCase, getByIdUseCase, mapper);

        client = WebTestClient.bindToController(controller)
                .build();
    }

    @Test
    void createRole() {
        var req         = new RequestRoleDTO("admin", "todos los permisos");
        var mappedRole  = new Role("admin", "todos los permisos");
        var savedRole   = new Role("admin", "todos los permisos");
        var resp        = new ResponseRoleDTO(1L, "admin", "todos los permisos");


        when(mapper.map(any(RequestRoleDTO.class), eq(Role.class)))
                .thenReturn(mappedRole);

        when(saveRoleUseCase.saveRole(any(Role.class)))
                .thenReturn(Mono.just(savedRole));

        when(mapper.map(eq(savedRole), eq(ResponseRoleDTO.class)))
                .thenReturn(resp);

        client.post()
                .uri("/api/v1/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseRoleDTO.class)
                .isEqualTo(resp);
    }

    @Test
    void getRoleById() {
        long id = 7L;

        var domain = new Role("admin", "todos los permisos");
        var resp   = new ResponseRoleDTO(id, "admin", "todos los permisos");


        when(getByIdUseCase.getRoleById(eq(id))).thenReturn(Mono.just(domain));

        when(mapper.map(eq(domain), eq(ResponseRoleDTO.class))).thenReturn(resp);

        client.get()
                .uri("/api/v1/roles/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseRoleDTO.class)
                .isEqualTo(resp);
    }

}

