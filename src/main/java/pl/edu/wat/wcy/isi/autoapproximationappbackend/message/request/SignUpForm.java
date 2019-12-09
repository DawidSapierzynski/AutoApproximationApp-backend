package pl.edu.wat.wcy.isi.autoapproximationappbackend.message.request;

import lombok.Getter;
import lombok.Setter;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.RoleUserDTO;

import java.util.Set;

import javax.validation.constraints.*;

public class SignUpForm {
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 50)
    private String login;

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;
    
    private Set<RoleUserDTO> role;
    
    @NotBlank
    @Size(min = 3, max = 40)
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleUserDTO> getRole() {
        return role;
    }

    public void setRole(Set<RoleUserDTO> role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}