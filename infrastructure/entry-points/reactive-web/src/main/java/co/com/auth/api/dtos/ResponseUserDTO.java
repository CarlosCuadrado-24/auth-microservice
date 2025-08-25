package co.com.auth.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseUserDTO {
    private Long id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private Long salary;
    private RequestRoleDTO role;
}
