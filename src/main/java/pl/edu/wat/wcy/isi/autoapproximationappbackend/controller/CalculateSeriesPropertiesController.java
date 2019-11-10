package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.FileForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.SeriesPropertiesServise;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/calculateProperties")
public class CalculateSeriesPropertiesController {
    private Logger logger = LoggerFactory.getLogger(CalculateSeriesPropertiesController.class);

    private final SeriesPropertiesServise seriesPropertiesServise;

    public CalculateSeriesPropertiesController(SeriesPropertiesServise seriesPropertiesServise) {
        this.seriesPropertiesServise = seriesPropertiesServise;
    }

    @PostMapping(produces = "application/json")
    public SeriesProperties getSeriesProperties(@RequestBody FileForm fileForm) {
        SeriesProperties seriesProperties = new SeriesProperties();
        seriesPropertiesServise.readFileForm(fileForm, seriesProperties);
        seriesPropertiesServise.propertiesCalculate(seriesProperties);

        return seriesProperties;
    }
}
