package pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.RoleUserDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.RoleUserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.repository.RoleUserRepository;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.RoleUserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleUserMapper {
    private RoleUserService roleUserService;

    public RoleUserMapper(RoleUserService roleUserService) {
        this.roleUserService = roleUserService;
    }

    public List<RoleUserDTO> buildRoleUserDTOs(Collection<RoleUserEntity> roleUserEntities) {
        return roleUserEntities.stream().map(this::buildRoleUserDTOs).collect(Collectors.toList());
    }

    public RoleUserDTO buildRoleUserDTOs(RoleUserEntity roleUserEntity) {
        return RoleUserDTO.builder()
                .id(roleUserEntity.getRoleUserId())
                .code(roleUserEntity.getCode())
                .name(roleUserEntity.getName())
                .build();
    }

    public List<RoleUserEntity> mapRoleUserEntities(Collection<RoleUserDTO> roleUserDTOs) {
        List<RoleUserEntity> roleUserEntities = new ArrayList<>();

        for (RoleUserDTO r : roleUserDTOs) {
            Optional<RoleUserEntity> roleUserEntity = findRoleUserEntity(r);
            roleUserEntity.ifPresent(roleUserEntities::add);
        }

        return roleUserEntities;
    }

    public Optional<RoleUserEntity> findRoleUserEntity(RoleUserDTO roleUserDTO) {
        return roleUserService.findById(roleUserDTO.getId());
    }


}
