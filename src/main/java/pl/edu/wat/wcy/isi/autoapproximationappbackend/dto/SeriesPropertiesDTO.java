package pl.edu.wat.wcy.isi.autoapproximationappbackend.dto;

import lombok.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.PointXY;

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
    private List<PointXY> points;
    private boolean deleted;

    public static SeriesPropertiesDTO bulid(SeriesPropertiesEntity seriesProperties){
        return SeriesPropertiesDTO.builder()
                .id(seriesProperties.getSeriesPropertiesId())
                .size(seriesProperties.getSize())
                .precision(seriesProperties.getPrecisionApproximation())
                .standardDeviation(seriesProperties.getStandardDeviation())
                .variance(seriesProperties.getVariance())
                .fastVariationPolynomial(seriesProperties.getFastVariationPolynomial())
                .fastVariationTrigonometric(seriesProperties.getFastVariationTrigonometric())
                .fastVariation(seriesProperties.getFastVariation().equals((byte) 1))
                .points(seriesProperties.getPoints())
                .deleted(seriesProperties.getDeleted().equals((byte) 1))
                .build();
    }
}
