package pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.UserDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.message.request.SignUpForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    private BCryptPasswordEncoder encoder;

    public UserMapper(BCryptPasswordEncoder encoder) {
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
                .active(userEntity.getActive().equals((byte) 1))
                .deleted(userEntity.getDeleted().equals((byte) 1))
                .build();
    }
}
