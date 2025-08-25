package co.com.auth.usecase.role;


import co.com.auth.model.role.Role;
import co.com.auth.model.role.gateways.RoleRepository;
import reactor.core.publisher.Mono;

public class SaveRoleUseCase {

    private final RoleRepository roleRepository;

    public SaveRoleUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Mono<Role> saveRole(Role role){
        validateNullOrBlank(role.getName(),"name");
        validateNullOrBlank(role.getDescription(),"description");
        return roleRepository.saveRole(role);
    }

    public void validateNullOrBlank(String campo,String nameCampo){
        if (campo == null || campo.isBlank()) {
            throw new IllegalArgumentException(nameCampo + " no acepta valores vacíos o nulos");
        }
    }

}
