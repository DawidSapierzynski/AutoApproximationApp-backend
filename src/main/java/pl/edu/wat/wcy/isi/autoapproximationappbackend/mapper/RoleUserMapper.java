package pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.RoleUserDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.RoleUserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.repository.RoleUserRepository;

import java.util.*;

@Service
public class RoleUserMapper {
    private RoleUserRepository roleUserRepository;

    public RoleUserMapper(RoleUserRepository roleUserRepository) {
        this.roleUserRepository = roleUserRepository;
    }

    public List<RoleUserDTO> buildRoleUserDTO(Collection<RoleUserEntity> roleUserEntities) {
        List<RoleUserDTO> roleUserDTOs = new ArrayList<>();

        for (RoleUserEntity r : roleUserEntities) {
            roleUserDTOs.add(buildRoleUserDTO(r));
        }

        return roleUserDTOs;
    }

    public RoleUserDTO buildRoleUserDTO(RoleUserEntity roleUserEntity) {
        return RoleUserDTO.builder()
                .id(roleUserEntity.getRoleUserId())
                .code(roleUserEntity.getCode())
                .name(roleUserEntity.getName())
                .build();
    }

    public List<RoleUserEntity> buildRoleUserEntity(Collection<RoleUserDTO> roleUserDTOs) {
        List<RoleUserEntity> roleUserEntities = new ArrayList<>();

        for (RoleUserDTO r : roleUserDTOs) {
            Optional<RoleUserEntity> roleUserEntity = buildRoleUserEntity(r);
            roleUserEntity.ifPresent(roleUserEntities::add);
        }

        return roleUserEntities;
    }

    public Optional<RoleUserEntity> buildRoleUserEntity(RoleUserDTO roleUserDTO) {
        return roleUserRepository.findById(roleUserDTO.getId());
    }


}
