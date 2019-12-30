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
public class DataSeriesFileDTO {
    private long id;
    private long userId;
    private String name;
    private String hashName;
    private Timestamp dateSent;
    private int size;
    private double fastVariationPolynomial;
    private double fastVariationTrigonometric;
    private boolean fastVariation;
    private double variance;
    private double standardDeviation;
    private boolean equidistant;
    private boolean deleted;
    private List<PointXY> points;
    private List<PointXY> artefacts;
}
