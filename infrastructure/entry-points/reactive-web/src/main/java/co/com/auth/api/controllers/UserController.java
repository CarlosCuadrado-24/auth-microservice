package co.com.auth.api.controllers;

import co.com.auth.api.dtos.RequestUserDTO;
import co.com.auth.api.dtos.ResponseUserDTO;
import co.com.auth.model.user.User;
import co.com.auth.usecase.role.GetByIdUseCase;
import co.com.auth.usecase.user.SaveUserUseCase;
import org.reactivecommons.utils.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {


    private final SaveUserUseCase userUseCase;
    private final GetByIdUseCase  getByIdUseCaseRole;
    private final ObjectMapper mapper;


    @PostMapping("/usuarios")
    public Mono<ResponseEntity<ResponseUserDTO>> saveUser(@RequestBody RequestUserDTO requestUserDTO) {
        User user = mapper.map(requestUserDTO, User.class);

        return getByIdUseCaseRole.getRoleById(requestUserDTO.getIdRole())
                .flatMap(role -> {
                    user.setRole(role);
                    return userUseCase.saveUser(user);
                })
                .map(saved -> ResponseEntity.ok(mapper.map(saved, ResponseUserDTO.class)));
    }
}
