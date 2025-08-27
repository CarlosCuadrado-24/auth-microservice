package co.com.auth.api.controllers;

import co.com.auth.api.dtos.RequestRoleDTO;
import co.com.auth.api.dtos.ResponseRoleDTO;
import co.com.auth.model.role.Role;
import co.com.auth.usecase.role.GetByIdUseCase;
import co.com.auth.usecase.role.SaveRoleUseCase;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RoleController {

    private final SaveRoleUseCase saveRoleUseCase;
    private final GetByIdUseCase getByIdUseCaseRole;
    private final ObjectMapper mapper;

    @PostMapping("/roles")
    public Mono<ResponseEntity<ResponseRoleDTO>> saveRole(@RequestBody RequestRoleDTO requestRoleDTO) {
        Role role = mapper.map(requestRoleDTO, Role.class);
        return saveRoleUseCase.saveRole(role)
                .map(saved -> ResponseEntity.ok(mapper.map(saved, ResponseRoleDTO.class)));
    }

    @GetMapping("/roles/{id}")
    public Mono<ResponseEntity<ResponseRoleDTO>> getRoleById(@PathVariable("id") Long id) {
        return getByIdUseCaseRole.getRoleById(id)
                .map(role -> ResponseEntity.ok(mapper.map(role, ResponseRoleDTO.class)));
    }
}
