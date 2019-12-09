package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.RoleUserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.RoleUserToUserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.repository.RoleUserToUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleUserToUserService {
    private RoleUserToUserRepository roleUserToUserRepository;

    public RoleUserToUserService(RoleUserToUserRepository roleUserToUserRepository) {
        this.roleUserToUserRepository = roleUserToUserRepository;
    }

    public void addRoleToUser(UserEntity userEntity, List<RoleUserEntity> roleUserEntities) {
        for (RoleUserEntity r : roleUserEntities) {
            RoleUserToUserEntity roleUserToUser = new RoleUserToUserEntity();
            roleUserToUser.setUserByUserId(userEntity);
            roleUserToUser.setRoleUserByRoleUserId(r);

            save(roleUserToUser);
        }
    }

    public RoleUserToUserEntity save(RoleUserToUserEntity roleUserToUserEntity) {
        return roleUserToUserRepository.save(roleUserToUserEntity);
    }

    public Optional<RoleUserToUserEntity> findById(Long id) {
        return roleUserToUserRepository.findById(id);
    }
}
