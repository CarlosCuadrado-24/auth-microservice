package co.com.auth.usecase.user;

import co.com.auth.model.role.Role;
import co.com.auth.model.user.User;
import co.com.auth.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class SaveUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SaveUserUseCase saveUserUseCase;

    private User user;
    private Role role;

    @BeforeEach
    void init(){
         role = new Role("admin","todos los permisos");
         user = new User("carlos","cuadrado", LocalDate.parse("1999-06-15"),"3001234567","carlos.cuadrado@example.com",2500000L,role);
    }

    @Test
    void saveUser() {

        when(userRepository.saveUser(user)).thenReturn(Mono.just(user));

        StepVerifier.create(saveUserUseCase.saveUser(this.user)).
                assertNext(userTest -> {
                    assertEquals("carlos",userTest.getName());
                    assertEquals("admin",userTest.getRole().getName());
                })
                .verifyComplete();
    }

    @Test
    void invalidEmail(){
        this.user.setEmail("email-incorrecto");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () ->saveUserUseCase.saveUser(user)
        );
        assertEquals("El email debe tener un formato valido",exception.getMessage());
    }

    @Test
    void invalidSalary(){
        this.user.setSalary(3000000000L);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () ->saveUserUseCase.saveUser(user)
        );
        assertEquals("El salario debe estar entre 1 y 15.000.000",exception.getMessage());
    }

    @Test
    void blankTest(){
        this.user.setLastName("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () ->saveUserUseCase.saveUser(user)
        );
        assertEquals("lastName no acepta valores vacíos o nulos",exception.getMessage());
    }

    @Test
    void testNull(){
        this.user.setLastName(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () ->saveUserUseCase.saveUser(user)
        );
        assertEquals("lastName no acepta valores vacíos o nulos",exception.getMessage());
    }



}