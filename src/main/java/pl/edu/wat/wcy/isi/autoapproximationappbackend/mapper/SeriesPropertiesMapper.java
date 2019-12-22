package pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.SeriesPropertiesDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesPropertiesMapper {

    public List<SeriesPropertiesDTO> bulidSeriesPropertiesDTOs(Collection<SeriesPropertiesEntity> seriesProperties) {
        return seriesProperties.stream()
                .map(this::bulidSeriesPropertiesDTO)
                .collect(Collectors.toList());
    }

    public SeriesPropertiesDTO bulidSeriesPropertiesDTO(SeriesPropertiesEntity seriesProperties) {
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
                .artefacts(seriesProperties.getArtefacts())
                .dateCreate(seriesProperties.getDateCreate())
                .deleted(seriesProperties.getDeleted().equals((byte) 1))
                .equidistant(seriesProperties.getEquidistant().equals((byte) 1))
                .build();
    }
}
