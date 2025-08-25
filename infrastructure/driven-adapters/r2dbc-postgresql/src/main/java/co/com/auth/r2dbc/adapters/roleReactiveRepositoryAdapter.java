package co.com.auth.r2dbc.adapters;

import co.com.auth.model.role.Role;
import co.com.auth.model.role.gateways.RoleRepository;
import co.com.auth.r2dbc.entities.RoleEntity;
import co.com.auth.r2dbc.helper.ReactiveAdapterOperations;
import co.com.auth.r2dbc.repositories.IRoleReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public class roleReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Role,
        RoleEntity,
        Long,
        IRoleReactiveRepository
        > implements RoleRepository {
    public roleReactiveRepositoryAdapter(IRoleReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Role.class));
    }

    @Transactional
    @Override
    public Mono<Role> saveRole(Role role) {
        return repository.save(mapper.map(role,RoleEntity.class))
                .map(saved->mapper.map(saved, Role.class));
    }

    @Transactional
    @Override
    public Mono<Role> getRoleById(Long id) {
        return repository.findById(id).map(roleEntity->mapper.map(roleEntity, Role.class));
    }
}
