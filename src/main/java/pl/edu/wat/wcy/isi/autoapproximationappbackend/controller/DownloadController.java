package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.MathematicalFunctionDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.message.request.GenerateDataSeriesForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.DownloadService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/download")
public class DownloadController {
    private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);

    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @PutMapping(value = "/approximation", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadApproximationResult(@RequestBody List<MathematicalFunctionDTO> mathematicalFunctionDTOs) {
        byte[] text = downloadService.getApproximationResult(mathematicalFunctionDTOs);

        logger.info("Downloading approximation results completed successfully.");
        return new ResponseEntity<>(text, HttpStatus.OK);
    }

    @PutMapping(value = "/generateDataSeries", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateDataSeries(@RequestBody GenerateDataSeriesForm dataSeriesForm) {
        byte[] text = downloadService.generateDataSeries(dataSeriesForm.isTrigonometricPolynomial(), dataSeriesForm.getMathematicalFunctionDTO(), dataSeriesForm.getNumberPoints());

        logger.info("Generating data series completed successfully.");
        return new ResponseEntity<>(text, HttpStatus.OK);
    }
}
