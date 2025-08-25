package co.com.auth.r2dbc.entities;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("roles")
public class RoleEntity {

    @Id
    private Long id;

    private String name;
    private String description;
}
