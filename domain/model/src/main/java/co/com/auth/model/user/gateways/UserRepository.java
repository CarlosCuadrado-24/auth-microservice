package co.com.auth.model.user.gateways;

import co.com.auth.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> saveUser(User user);
}
