package pl.edu.wat.wcy.isi.autoapproximationappbackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.DataSeriesFileEntity;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class DataSeriesFileDTO {
    private long id;
    private String name;
    private String hashName;
    private Timestamp dataSent;
    private boolean deleted;

    public static DataSeriesFileDTO bulid(DataSeriesFileEntity dataSeriesFileEntity) {
        return DataSeriesFileDTO.builder()
                .id(dataSeriesFileEntity.getDataSeriesFileId())
                .name(dataSeriesFileEntity.getName())
                .hashName(dataSeriesFileEntity.getHashName())
                .dataSent(dataSeriesFileEntity.getDataSent())
                .deleted(dataSeriesFileEntity.getDeleted().equals((byte) 1))
                .build();
    }
}
