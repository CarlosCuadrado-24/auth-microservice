package co.com.auth.r2dbc.adapters;

import co.com.auth.model.role.Role;
import co.com.auth.model.role.gateways.RoleRepository;
import co.com.auth.r2dbc.entities.RoleEntity;
import co.com.auth.r2dbc.repositories.IRoleReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleReactiveRepositoryAdapterTest {

    @Mock
    IRoleReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    RoleRepository adapter;

    private Role domainRole;
    private RoleEntity entityRole;

    @BeforeEach
    void setUp() {
        domainRole = new Role(1L,"admin", "todos los permisos");
        entityRole = new RoleEntity();
        entityRole.setId(1L);
        entityRole.setName("admin");
        entityRole.setDescription("todos los permisos");


        adapter = new RoleReactiveRepositoryAdapter(repository, mapper);
    }


    @Test
    void saveRole() {

        when(mapper.map(domainRole, RoleEntity.class)).thenReturn(entityRole);
        when(repository.save(entityRole)).thenReturn(Mono.just(entityRole));
        when(mapper.map(entityRole, Role.class)).thenReturn(domainRole);

        StepVerifier.create(adapter.saveRole(domainRole))
                .assertNext(roleTest -> {
                    assertEquals("admin", roleTest.getName());
                    assertEquals("todos los permisos", roleTest.getDescription());
                    assertEquals(1L,roleTest.getId());
                })
                .verifyComplete();

        verify(mapper).map(domainRole, RoleEntity.class);
        verify(repository).save(entityRole);
        verify(mapper).map(entityRole, Role.class);
    }


    @Test
    void getRoleById() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Mono.just(entityRole));
        when(mapper.map(entityRole, Role.class)).thenReturn(domainRole);

        StepVerifier.create(adapter.getRoleById(id))
                .assertNext(roleTest -> {
                    assertEquals("admin", roleTest.getName());
                    assertEquals("todos los permisos", roleTest.getDescription());
                })
                .verifyComplete();

        verify(repository).findById(id);
        verify(mapper).map(entityRole, Role.class);
    }

}