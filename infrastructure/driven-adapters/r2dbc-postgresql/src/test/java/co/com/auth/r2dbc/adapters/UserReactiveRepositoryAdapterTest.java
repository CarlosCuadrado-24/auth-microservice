package co.com.auth.r2dbc.adapters;

import co.com.auth.model.role.Role;
import co.com.auth.model.user.User;
import co.com.auth.model.user.gateways.UserRepository;
import co.com.auth.r2dbc.entities.UserEntity;
import co.com.auth.r2dbc.repositories.IUserReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {

    @Mock
    IUserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    UserRepository adapter;

    private User domainUserWithRole;
    private User domainUserNoRole;
    private UserEntity entityToSaveWithRoleId;
    private UserEntity savedEntity;

    private Role role;

    @BeforeEach
    void setUp() {
        // Domain role & users
        role = new Role();
        role.setId(7L);
        role.setName("admin");
        role.setDescription("todos los permisos");

        domainUserWithRole = new User(
                "Carlos", "Cuadrado",
                LocalDate.parse("1999-06-15"),
                "3001234567",
                "carlos.cuadrado@example.com",
                2_500_000L,
                role
        );

        domainUserNoRole = new User(
                "Carlos", "Cuadrado",
                LocalDate.parse("1999-06-15"),
                "3001234567",
                "carlos.cuadrado@example.com",
                2_500_000L,
                null
        );

        // Entities
        entityToSaveWithRoleId = new UserEntity();
        entityToSaveWithRoleId.setName("Carlos");
        entityToSaveWithRoleId.setLastName("Cuadrado");
        entityToSaveWithRoleId.setPhone("3001234567");
        entityToSaveWithRoleId.setEmail("carlos.cuadrado@example.com");
        entityToSaveWithRoleId.setSalary(2_500_000L);
        entityToSaveWithRoleId.setRoleId(7L);

        savedEntity = new UserEntity();
        savedEntity.setId(123L);
        savedEntity.setName("Carlos");
        savedEntity.setLastName("Cuadrado");
        savedEntity.setPhone("3001234567");
        savedEntity.setEmail("carlos.cuadrado@example.com");
        savedEntity.setSalary(2_500_000L);
        savedEntity.setRoleId(7L);

        adapter = new UserReactiveRepositoryAdapter(repository, mapper);
    }

    @Test
    void saveUser() {

        when(mapper.map(domainUserWithRole, UserEntity.class)).thenReturn(new UserEntity() {{
            setName("Carlos");
            setLastName("Cuadrado");
            setPhone("3001234567");
            setEmail("carlos.cuadrado@example.com");
            setSalary(2_500_000L);
        }});


        when(repository.save(argThat(e ->
                e.getRoleId() != null && e.getRoleId().equals(7L) &&
                        "Carlos".equals(e.getName()) && "Cuadrado".equals(e.getLastName())
        ))).thenReturn(Mono.just(savedEntity));


        when(mapper.map(savedEntity, User.class)).thenReturn(new User(
                "Carlos", "Cuadrado",
                LocalDate.parse("1999-06-15"),
                "3001234567",
                "carlos.cuadrado@example.com",
                2_500_000L,
                null
        ));

        StepVerifier.create(adapter.saveUser(domainUserWithRole))
                .assertNext(out -> {
                    assertEquals("Carlos", out.getName());
                    assertEquals("Cuadrado", out.getLastName());
                    assertEquals("carlos.cuadrado@example.com", out.getEmail());
                    assertNotNull(out.getRole());
                    assertEquals(7L, out.getRole().getId());
                    assertEquals("admin", out.getRole().getName());
                })
                .verifyComplete();

        verify(mapper).map(domainUserWithRole, UserEntity.class);
        verify(repository).save(argThat(e -> e.getRoleId() != null && e.getRoleId().equals(7L)));
        verify(mapper).map(savedEntity, User.class);
    }
}