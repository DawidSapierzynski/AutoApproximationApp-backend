package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.ChosenMethod;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.SeriesProperties;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.ChooseMethodService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/chooseMethod")
public class ChooseMethodController {
    private Logger logger = LoggerFactory.getLogger(ChooseMethodController.class);

    private ChooseMethodService chooseMethodService;

    public ChooseMethodController(ChooseMethodService chooseMethodService) {
        this.chooseMethodService = chooseMethodService;
    }

    @PostMapping(produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public List<ChosenMethod> getSeriesProperties(@RequestBody SeriesProperties seriesProperties) {
        List<ChosenMethod> chosenMethods;
        chosenMethods = chooseMethodService.selectMethods(seriesProperties);

        return chosenMethods;
    }
}
