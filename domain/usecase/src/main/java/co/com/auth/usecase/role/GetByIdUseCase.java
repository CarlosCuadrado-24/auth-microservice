package co.com.auth.usecase.role;

import co.com.auth.model.role.Role;
import co.com.auth.model.role.gateways.RoleRepository;
import reactor.core.publisher.Mono;

public class GetByIdUseCase {
    private final RoleRepository roleRepository;

    public GetByIdUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Mono<Role> getRoleById(Long id){
        validateNull(id);
        validateFormat(id);
        return roleRepository.getRoleById(id);
    }

    public void validateNull(Long id){
        if (id == null) {
            throw new IllegalArgumentException("el id no puede ser null");
        }
    }

    public void validateFormat(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id debe ser un número entero positivo");
        }
    }




}
