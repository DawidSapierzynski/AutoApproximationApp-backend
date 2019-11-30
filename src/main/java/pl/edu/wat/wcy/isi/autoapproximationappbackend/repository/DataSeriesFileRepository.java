package pl.edu.wat.wcy.isi.autoapproximationappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.models.DataSeriesFileEntity;

@Repository
public interface DataSeriesFileRepository extends JpaRepository<DataSeriesFileEntity, Long> {
}
