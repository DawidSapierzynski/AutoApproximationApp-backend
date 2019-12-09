package pl.edu.wat.wcy.isi.autoapproximationappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.RoleUserToUserEntity;

@Repository
public interface RoleUserToUserRepository extends JpaRepository<RoleUserToUserEntity, Long> {
}
