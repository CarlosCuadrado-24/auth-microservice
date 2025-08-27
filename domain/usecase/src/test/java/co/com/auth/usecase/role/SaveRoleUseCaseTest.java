package co.com.auth.usecase.role;

import co.com.auth.model.role.Role;
import co.com.auth.model.role.gateways.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveRoleUseCaseTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private SaveRoleUseCase saveRoleUseCase;

    private Role role;

    @BeforeEach
    void init() {
        role = new Role("admin", "todos los permisos");
    }

    @Test
    void saveRole() {
        when(roleRepository.saveRole(role)).thenReturn(Mono.just(role));

        StepVerifier.create(saveRoleUseCase.saveRole(role))
                .assertNext(roleTest -> {
                    assertEquals("admin", roleTest.getName());
                    assertEquals("todos los permisos", roleTest.getDescription());
                })
                .verifyComplete();
    }

    @Test
    void blankTest() {
        role.setName("   ");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> saveRoleUseCase.saveRole(role)
        );
        assertEquals("name no acepta valores vacíos o nulos", ex.getMessage());

    }

    @Test
    void nullTest() {
        role.setDescription(null);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> saveRoleUseCase.saveRole(role)
        );
        assertEquals("description no acepta valores vacíos o nulos", ex.getMessage());

    }


}