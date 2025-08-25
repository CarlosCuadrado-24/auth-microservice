package co.com.auth.r2dbc.entities;



import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("users")
public class
UserEntity {
    @Id
    private Long id;

    private String name;

    @Column("last_name")
    private String lastName;

    @Column("birth_date")
    private LocalDate birthDate;

    private String phone;
    private String email;
    private Long salary;

    @Column("roles_id")
    private Long roleId;

}
