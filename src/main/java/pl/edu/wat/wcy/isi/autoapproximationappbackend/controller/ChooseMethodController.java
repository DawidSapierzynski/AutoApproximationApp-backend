package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.ChosenMethod;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.ChooseMethodServise;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/chooseMethod")
public class ChooseMethodController {
    private Logger logger = LoggerFactory.getLogger(ChooseMethodController.class);

    private ChooseMethodServise chooseMethodServise;

    public ChooseMethodController(ChooseMethodServise chooseMethodServise) {
        this.chooseMethodServise = chooseMethodServise;
    }

    @PostMapping(produces = "application/json")
    public List<ChosenMethod> getSeriesProperties(@RequestBody SeriesProperties seriesProperties) {
        List<ChosenMethod> chosenMethods;
        chosenMethods = chooseMethodServise.selectMethods(seriesProperties);

        return chosenMethods;
    }
}
