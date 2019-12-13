package pl.edu.wat.wcy.isi.autoapproximationappbackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.RoleUserEntity;

import java.util.Collection;

@Getter
@Setter
@Builder
public class UserDTO {
    private long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private Collection<RoleUserDTO> rolesUserDto;
    private boolean deleted;
    private boolean active;
}
