package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ApproximationDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.message.request.ApproximationForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.ApproximationService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/doApproximations")
public class ApproximationController {
    private final Logger logger = LoggerFactory.getLogger(ApproximationController.class);

    private final ApproximationService approximationService;

    public ApproximationController(ApproximationService approximationService) {
        this.approximationService = approximationService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<ApproximationDTO> getSeriesProperties(@RequestBody ApproximationForm approximationForm) {
        ApproximationDTO approximationDTO = new ApproximationDTO();

        approximationService.doApproximations(approximationForm.getChosenMethod(), approximationForm.getPoints(), approximationDTO);
        logger.info("Approximation successful - {}.", approximationForm.getChosenMethod().getMethod());

        return new ResponseEntity<>(approximationDTO, HttpStatus.OK);
    }
}
