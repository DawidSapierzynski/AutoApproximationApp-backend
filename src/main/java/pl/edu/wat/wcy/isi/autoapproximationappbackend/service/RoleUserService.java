package pl.edu.wat.wcy.isi.autoapproximationappbackend.service;

import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.RoleUserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.repository.RoleUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleUserService {
    private RoleUserRepository roleUserRepository;

    public RoleUserService(RoleUserRepository roleUserRepository) {
        this.roleUserRepository = roleUserRepository;
    }

    public RoleUserEntity save(RoleUserEntity roleUserEntity){
        return roleUserRepository.save(roleUserEntity);
    }

    public Optional<RoleUserEntity> findById (Long id) {
        return roleUserRepository.findById(id);
    }

    public Optional<RoleUserEntity> findByCode (String code) {
        return roleUserRepository.findByCode(code);
    }

    public List<RoleUserEntity> getAll(){
        return roleUserRepository.findAll();
    }
}
