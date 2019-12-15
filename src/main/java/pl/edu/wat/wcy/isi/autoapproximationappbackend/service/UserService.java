package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.security.UserPrinciple;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.UserDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.RoleUserMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.RoleUserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleUserToUserService roleUserToUserService;
    private RoleUserMapper roleUserMapper;

    public UserService(UserRepository userRepository, RoleUserToUserService roleUserToUserService, RoleUserMapper roleUserMapper) {
        this.userRepository = userRepository;
        this.roleUserToUserService = roleUserToUserService;
        this.roleUserMapper = roleUserMapper;
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public boolean existsByLogin(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public UserEntity getLoggedUser() {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(userPrinciple.getId()).orElseThrow();
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public UserEntity delete(UserEntity userEntity) {
        userEntity.setDeleted((byte) 1);
        return save(userEntity);
    }

    public UserEntity update(UserEntity user, UserDTO userDTO) {
        List<RoleUserEntity> rolesUser = this.roleUserMapper.mapRoleUserEntities(userDTO.getRolesUserDto());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setActive(userDTO.isActive() ? (byte) 1 : (byte) 0);

        this.roleUserToUserService.deleteByUser(user);
        this.roleUserToUserService.addRoleToUser(user, rolesUser);

        return save(user);
    }

    public boolean findByEmailAndLoginNot(String email, String login){
        return this.userRepository.findByEmailAndLoginNot(email, login).isPresent();
    }
}
