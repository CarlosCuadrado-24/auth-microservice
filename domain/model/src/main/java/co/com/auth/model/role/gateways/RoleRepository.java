package co.com.auth.model.role.gateways;

import co.com.auth.model.role.Role;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    Mono<Role> saveRole(Role role);
    Mono<Role> getRoleById(Long id);
}
