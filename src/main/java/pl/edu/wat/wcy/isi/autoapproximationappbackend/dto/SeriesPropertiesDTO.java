package pl.edu.wat.wcy.isi.autoapproximationappbackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
public class SeriesPropertiesDTO {
    private long id;
    private int size;
    private double fastVariationPolynomial;
    private double fastVariationTrigonometric;
    private boolean fastVariation;
    private double variance;
    private double standardDeviation;
    private int precision;
    private Timestamp dateCreate;
    private boolean deleted;
    private List<PointXY> points;
    private List<PointXY> artefacts;

}
