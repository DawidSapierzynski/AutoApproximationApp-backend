package pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.DataSeriesFileDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.DataSeriesFileEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataSeriesFileMapper {

    public List<DataSeriesFileDTO> buildDataSeriesFiles(List<DataSeriesFileEntity> dataSeriesFiles) {
        return dataSeriesFiles.stream().map(this::buildDataSeriesFile).collect(Collectors.toList());
    }

    public DataSeriesFileDTO buildDataSeriesFile(DataSeriesFileEntity dataSeriesFile) {
        return DataSeriesFileDTO.builder()
                .id(dataSeriesFile.getDataSeriesFileId())
                .name(dataSeriesFile.getName())
                .hashName(dataSeriesFile.getHashName())
                .dataSent(dataSeriesFile.getDataSent())
                .deleted(dataSeriesFile.getDeleted().equals((byte) 1))
                .build();
    }
}
