package pl.edu.wat.wcy.isi.autoapproximationappbackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class DataSeriesFileDTO {
    private long id;
    private String name;
    private String hashName;
    private Timestamp dateSent;
    private boolean deleted;
}
