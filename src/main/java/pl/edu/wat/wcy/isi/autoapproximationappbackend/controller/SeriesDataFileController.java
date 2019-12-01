package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.StorageService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/seriesDataFile")
public class SeriesDataFileController {
    private Logger logger = LoggerFactory.getLogger(SeriesDataFileController.class);
    private final StorageService storageService;

    public SeriesDataFileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public Long uploadFile(@RequestParam("dataSeriesFile") MultipartFile dataSeriesFile) {

        storageService.store(dataSeriesFile, dataSeriesFile.getOriginalFilename());

        return Long.parseLong(dataSeriesFile.getOriginalFilename().split("[.]")[0]);
    }
}
