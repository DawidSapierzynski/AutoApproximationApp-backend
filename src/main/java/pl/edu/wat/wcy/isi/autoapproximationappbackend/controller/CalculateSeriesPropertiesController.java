package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.SeriesPropertiesService;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.StorageService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/calculateProperties")
public class CalculateSeriesPropertiesController {
    private Logger logger = LoggerFactory.getLogger(CalculateSeriesPropertiesController.class);

    private final SeriesPropertiesService seriesPropertiesService;
    private final StorageService storageService;

    public CalculateSeriesPropertiesController(SeriesPropertiesService seriesPropertiesService, StorageService storageService) {
        this.seriesPropertiesService = seriesPropertiesService;
        this.storageService = storageService;
    }

    @PostMapping(produces = "application/json")
    public SeriesProperties getSeriesProperties(@RequestParam("seriesDatesFile") MultipartFile seriesDatesFile, @RequestParam("precision") int precision) {
        String id = "Test";
        SeriesProperties seriesProperties = new SeriesProperties();
        storageService.store(seriesDatesFile, id);
        seriesPropertiesService.readFileForm(id, precision, seriesProperties);
        seriesPropertiesService.propertiesCalculate(seriesProperties);

        return seriesProperties;
    }
}
