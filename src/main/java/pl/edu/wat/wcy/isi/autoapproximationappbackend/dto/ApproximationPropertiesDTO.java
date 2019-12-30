package pl.edu.wat.wcy.isi.autoapproximationappbackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class ApproximationPropertiesDTO {
    private long id;
    private long userId;
    private int precision;
    private DataSeriesFileDTO dataSeriesFileDTO;
    private Timestamp dateCreate;
    private boolean deleted;
}
