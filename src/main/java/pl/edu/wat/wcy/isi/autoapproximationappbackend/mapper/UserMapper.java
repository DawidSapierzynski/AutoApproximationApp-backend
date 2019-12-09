package pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.RoleUserDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.UserDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.message.request.SignUpForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.RoleUserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserMapper {
    private RoleUserMapper roleUserMapper;
    private BCryptPasswordEncoder encoder;

    public UserMapper(RoleUserMapper roleUserMapper, BCryptPasswordEncoder encoder) {
        this.roleUserMapper = roleUserMapper;
        this.encoder = encoder;
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

        return userEntity;
    }



    public List<UserDTO> buildUserDTO(Collection<UserEntity> userEntities) {
        List<UserDTO> userDTOs = new ArrayList<>();

        for (UserEntity u : userEntities) {
            userDTOs.add(buildUserDTO(u));
        }

        return userDTOs;
    }

    public UserDTO buildUserDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .login(userEntity.getLogin())
                .email(userEntity.getEmail())
                .active(userEntity.getActive().equals((byte) 1))
                .deleted(userEntity.getDeleted().equals((byte) 1))
                .build();
    }
}
