package co.com.auth.usecase.user;


import co.com.auth.model.user.User;
import co.com.auth.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

public class SaveUserUseCase {

    private final UserRepository userRepository;

    public SaveUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> saveUser(User user){
        validateNullOrBlank(user.getName(),"name");
        validateNullOrBlank(user.getLastName(),"lastName");
        validateNullOrBlank(user.getEmail(),"email");
        validateSalary(user.getSalary());
        validateFormatEmail(user.getEmail());
        return userRepository.saveUser(user);
    }


    public void validateNullOrBlank(String campo,String nameCampo){
        if (campo == null || campo.isBlank()) {
            throw new IllegalArgumentException(nameCampo + " no acepta valores vacíos o nulos");
        }
    }


    public void validateSalary(Long salary){
        if (salary == null || salary <= 0 || salary > 15_000_000) {
            throw new IllegalArgumentException("El salario debe estar entre 1 y 15.000.000");
        }
    }


    public void validateFormatEmail(String email){
        if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            throw new IllegalArgumentException("El email debe tener un formato valido");
        }
    }

}
