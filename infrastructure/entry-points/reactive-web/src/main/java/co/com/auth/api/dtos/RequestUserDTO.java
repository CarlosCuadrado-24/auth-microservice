package co.com.auth.api.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RequestUserDTO {
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private Long salary;
    private Long idRole;
}
