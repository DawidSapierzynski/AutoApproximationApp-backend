package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.ApproximationDataForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.ApproximationForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.ApproximationService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/doApproximations")
public class ApproximationController {
    private Logger logger = LoggerFactory.getLogger(CalculateSeriesPropertiesController.class);

    private ApproximationService approximationService;

    public ApproximationController(ApproximationService approximationService) {
        this.approximationService = approximationService;
    }

    @PostMapping(produces = "application/json")
    public ApproximationForm getSeriesProperties(@RequestBody ApproximationDataForm approximationDataForm) {
        ApproximationForm approximationForm = new ApproximationForm();

        approximationService.doApproximations(approximationDataForm.getChosenMethod(), approximationDataForm.getPoints(), approximationForm);

        return approximationForm;
    }
}
