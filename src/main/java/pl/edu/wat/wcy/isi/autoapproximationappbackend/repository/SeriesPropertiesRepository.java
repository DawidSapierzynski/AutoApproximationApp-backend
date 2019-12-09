package pl.edu.wat.wcy.isi.autoapproximationappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;

import java.util.List;

@Repository
public interface SeriesPropertiesRepository extends JpaRepository<SeriesPropertiesEntity, Long> {
    List<SeriesPropertiesEntity> findByUserAndDeleted(UserEntity userEntity, byte deleted);
}
