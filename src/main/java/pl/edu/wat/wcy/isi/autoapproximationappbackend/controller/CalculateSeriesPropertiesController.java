package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.SeriesPropertiesService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/calculateProperties")
public class CalculateSeriesPropertiesController {
    private Logger logger = LoggerFactory.getLogger(CalculateSeriesPropertiesController.class);

    private final SeriesPropertiesService seriesPropertiesService;

    public CalculateSeriesPropertiesController(SeriesPropertiesService seriesPropertiesService) {
        this.seriesPropertiesService = seriesPropertiesService;
    }

    @PostMapping(produces = "application/json")
    public SeriesProperties getSeriesProperties(@RequestParam("dataSeriesFileId") Long dataSeriesFileId, @RequestParam("precision") int precision) {
        //TODO
        //Dodać do konstruktora seriesDataFileId
        SeriesProperties seriesProperties = new SeriesProperties();

        seriesPropertiesService.readFileForm(dataSeriesFileId, precision, seriesProperties);
        seriesPropertiesService.propertiesCalculate(seriesProperties);

        return seriesProperties;
    }
}
