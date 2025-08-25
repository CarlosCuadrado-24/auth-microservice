package co.com.auth.r2dbc.adapters;

import co.com.auth.model.user.User;
import co.com.auth.model.user.gateways.UserRepository;
import co.com.auth.r2dbc.entities.UserEntity;
import co.com.auth.r2dbc.helper.ReactiveAdapterOperations;
import co.com.auth.r2dbc.repositories.IUserReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public class userReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    User, UserEntity,
    Long,
        IUserReactiveRepository
> implements UserRepository {
    public userReactiveRepositoryAdapter(IUserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Transactional
    @Override
    public Mono<User> saveUser(User user) {
        UserEntity entity = mapper.map(user, UserEntity.class);

        entity.setRoleId(user.getRole() != null ? user.getRole().getId() : null);

        return repository.save(entity)
                .map(saved -> {
                    User out = mapper.map(saved, User.class);
                    out.setRole(user.getRole());
                    return out;
                });
    }

}
