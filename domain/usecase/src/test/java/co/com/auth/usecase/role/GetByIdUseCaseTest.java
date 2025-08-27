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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetByIdUseCaseTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private GetByIdUseCase getByIdUseCase;

    private Role role;

    @BeforeEach
    void init() {
        role = new Role(1L,"admin", "todos los permisos");

    }

    @Test
    void getRoleById() {
        Long id = 1L;
        when(roleRepository.getRoleById(id)).thenReturn(Mono.just(role));

        StepVerifier.create(getByIdUseCase.getRoleById(id))
                .assertNext(roleTest -> {
                    assertEquals("admin", roleTest.getName());
                    assertEquals("todos los permisos", roleTest.getDescription());
                })
                .verifyComplete();
    }


    @Test
    void nullTest() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> getByIdUseCase.getRoleById(null)
        );
        assertEquals("el id no puede ser null", ex.getMessage());

    }

    @Test
    void negativeOrZeroId() {
        IllegalArgumentException ex0 = assertThrows(
                IllegalArgumentException.class,
                () -> getByIdUseCase.getRoleById(0L)
        );
        assertEquals("El id debe ser un número entero positivo", ex0.getMessage());

        IllegalArgumentException exNeg = assertThrows(
                IllegalArgumentException.class,
                () -> getByIdUseCase.getRoleById(-5L)
        );
        assertEquals("El id debe ser un número entero positivo", exNeg.getMessage());
    }

}