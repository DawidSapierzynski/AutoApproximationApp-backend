package pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.RoleUserDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.UserDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.message.request.SignUpForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserRole;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.RoleUserService;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    private BCryptPasswordEncoder encoder;
    private RoleUserMapper roleUserMapper;
    private RoleUserService roleUserService;

    public UserMapper(BCryptPasswordEncoder encoder, RoleUserMapper roleUserMapper, RoleUserService roleUserService) {
        this.encoder = encoder;
        this.roleUserMapper = roleUserMapper;
        this.roleUserService = roleUserService;
    }

    public UserEntity buildUserEntity(SignUpForm signUpForm) {
        UserEntity userEntity = new UserEntity();

        userEntity.setActive((byte) 1);
        userEntity.setDeleted((byte) 0);
        userEntity.setLogin(signUpForm.getLogin());
        userEntity.setFirstName(signUpForm.getFirstName());
        userEntity.setLastName(signUpForm.getLastName());
        userEntity.setPassword(encoder.encode(signUpForm.getPassword()));
        userEntity.setEmail(signUpForm.getEmail());
        userEntity.setAdmin(isAdmin(signUpForm.getRole()));

        return userEntity;
    }

    private Byte isAdmin(Set<RoleUserDTO> roles) {
        for (RoleUserDTO r : roles) {
            if (r.getCode().equals(UserRole.ADMIN.getCode())) {
                return (byte) 1;
            }
        }

        return (byte) 0;
    }


    public List<UserDTO> buildUserDTOs(Collection<UserEntity> userEntities) {
        return userEntities.stream().map(this::buildUserDTO).collect(Collectors.toList());
    }

    public UserDTO buildUserDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .login(userEntity.getLogin())
                .email(userEntity.getEmail())
                .rolesUserDto(roleUserMapper.buildRoleUserDTOs(roleUserService.findByUser(userEntity)))
                .active(userEntity.getActive().equals((byte) 1))
                .deleted(userEntity.getDeleted().equals((byte) 1))
                .admin(userEntity.getAdmin().equals((byte) 1))
                .build();
    }
}
