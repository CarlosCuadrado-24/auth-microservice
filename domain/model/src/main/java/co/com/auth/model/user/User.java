package co.com.auth.model.user;


import co.com.auth.model.role.Role;

import java.time.LocalDate;
import java.util.Date;

public class User {
    private Long id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private Long salary;
    private Role role;

    public User(String name, String lastName, LocalDate birthDate, String phone, String email, Long salary, Role role) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.role = role;
    }

    public User(String name, String lastName, LocalDate birthDate, String phone, String email, Long salary) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
    }

    public User(Long id, String name, String lastName, LocalDate birthDate, String phone, String email, Long salary, Role role) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.role = role;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
