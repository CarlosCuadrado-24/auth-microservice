package co.com.auth.r2dbc.adapters;

import co.com.auth.model.role.Role;
import co.com.auth.model.role.gateways.RoleRepository;
import co.com.auth.r2dbc.entities.RoleEntity;
import co.com.auth.r2dbc.helper.ReactiveAdapterOperations;
import co.com.auth.r2dbc.repositories.IRoleReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class RoleReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Role,
        RoleEntity,
        Long,
        IRoleReactiveRepository
        > implements RoleRepository {
    public RoleReactiveRepositoryAdapter(IRoleReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Role.class));
    }

    @Transactional
    @Override
    public Mono<Role> saveRole(Role role) {
        log.info("Iniciando guardado del rol: {}", role);
        return repository.save(mapper.map(role,RoleEntity.class))
                .doOnSubscribe(sub -> log.debug("Guardando entidad en base de datos: {}", role))
                .doOnSuccess(saved -> log.info("rol guardado exitosamente con ID: {}", saved.getId()))
                .doOnError(error -> log.error("Error al guardar el rol: {}", error.getMessage(), error))
                .map(saved->mapper.map(saved, Role.class));
    }

    @Transactional
    @Override
    public Mono<Role> getRoleById(Long id) {
        log.info("Iniciando busqueda por id: {}", id);
        return repository.findById(id)
                .doOnSuccess(saved -> log.info("rol encontrado exitosamente con ID: {}", saved))
                .doOnError(error -> log.error("Error al ecnontrar el rol: {}", error.getMessage(), error))
                .map(roleEntity->mapper.map(roleEntity, Role.class));
    }
}
