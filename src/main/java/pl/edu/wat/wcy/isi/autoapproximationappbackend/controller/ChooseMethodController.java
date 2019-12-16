package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.ChosenMethodDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.SeriesPropertiesDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.ChooseMethodService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/chooseMethod")
public class ChooseMethodController {
    private final Logger logger = LoggerFactory.getLogger(ChooseMethodController.class);

    private final ChooseMethodService chooseMethodService;

    public ChooseMethodController(ChooseMethodService chooseMethodService) {
        this.chooseMethodService = chooseMethodService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<List<ChosenMethodDTO>> getMethods(@RequestBody SeriesPropertiesDTO seriesPropertiesDTO) {
        List<ChosenMethodDTO> chosenMethodDTOS;
        chosenMethodDTOS = chooseMethodService.selectMethods(seriesPropertiesDTO);

        logger.info("Choosing successful methods. Number of methods: {}", chosenMethodDTOS.size());
        return new ResponseEntity<>(chosenMethodDTOS, HttpStatus.OK);
    }
}
