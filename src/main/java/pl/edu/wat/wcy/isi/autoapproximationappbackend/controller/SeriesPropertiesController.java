package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.SeriesPropertiesDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.message.response.ResponseMessage;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.exception.ForbiddenException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.exception.ResourceNotFoundException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.exception.SizeException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.SeriesPropertiesMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.DataSeriesFileEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.DataSeriesFileService;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.SeriesPropertiesService;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.UserService;

import java.sql.Timestamp;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/seriesProperties")
public class SeriesPropertiesController {
    private final Logger logger = LoggerFactory.getLogger(SeriesPropertiesController.class);

    private final SeriesPropertiesService seriesPropertiesService;
    private final DataSeriesFileService dataSeriesFileService;
    private final UserService userService;
    private final SeriesPropertiesMapper seriesPropertiesMapper;

    public SeriesPropertiesController(SeriesPropertiesService seriesPropertiesService,
                                      DataSeriesFileService dataSeriesFileService, UserService userService,
                                      SeriesPropertiesMapper seriesPropertiesMapper) {
        this.seriesPropertiesService = seriesPropertiesService;
        this.dataSeriesFileService = dataSeriesFileService;
        this.userService = userService;
        this.seriesPropertiesMapper = seriesPropertiesMapper;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<SeriesPropertiesDTO> postSeriesProperties(@RequestParam("dataSeriesFileId") long dataSeriesFileId, @RequestParam("precision") int precision) throws SizeException, ResourceNotFoundException {

        SeriesPropertiesEntity seriesProperties = new SeriesPropertiesEntity();
        DataSeriesFileEntity dataSeriesFileEntity = dataSeriesFileService.findById(dataSeriesFileId)
                .orElseThrow(() -> new ResourceNotFoundException("Data series file not found for this id: " + dataSeriesFileId));
        UserEntity userEntity = userService.getLoggedUser();

        seriesProperties.setDataSeriesFile(dataSeriesFileEntity);
        seriesProperties.setDeleted((byte) 0);
        seriesProperties.setUser(userEntity);
        seriesProperties.setPrecisionApproximation(precision);
        seriesProperties.setDateCreate(new Timestamp(System.currentTimeMillis()));

        seriesPropertiesService.readFile(dataSeriesFileId, seriesProperties);
        seriesPropertiesService.propertiesCalculate(seriesProperties);
        seriesProperties = seriesPropertiesService.save(seriesProperties);

        logger.info("Adding series properties completed successfully.");
        return new ResponseEntity<>(seriesPropertiesMapper.bulidSeriesPropertiesDTO(seriesProperties), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SeriesPropertiesDTO>> getSeriesPropertiesUser() {
        UserEntity userEntity = userService.getLoggedUser();

        List<SeriesPropertiesEntity> seriesPropertiesEntities = seriesPropertiesService.findByUserAndDeleted(userEntity, (byte) 0);
        List<SeriesPropertiesDTO> seriesPropertiesDTOs = seriesPropertiesMapper.bulidSeriesPropertiesDTOs(seriesPropertiesEntities);

        logger.info("Getting all (for user) the series properties successfully completed. Size: {}", seriesPropertiesDTOs.size());
        return new ResponseEntity<>(seriesPropertiesDTOs, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/all")
    public ResponseEntity<List<SeriesPropertiesDTO>> getSeriesPropertiesAdmin() {
        List<SeriesPropertiesEntity> seriesPropertiesEntities = seriesPropertiesService.findAll();
        List<SeriesPropertiesDTO> seriesPropertiesDTOs = seriesPropertiesMapper.bulidSeriesPropertiesDTOs(seriesPropertiesEntities);

        logger.info("Getting all the series properties successfully completed. Size: {}", seriesPropertiesDTOs.size());
        return new ResponseEntity<>(seriesPropertiesDTOs, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/{seriesPropertiesId}")
    public ResponseEntity<SeriesPropertiesDTO> getSeriesProperties(@PathVariable long seriesPropertiesId) throws ResourceNotFoundException, ForbiddenException, SizeException {
        SeriesPropertiesEntity seriesProperties = seriesPropertiesService.findByIdAndDeleted(seriesPropertiesId, (byte) 0)
                .orElseThrow(() -> new ResourceNotFoundException("Series properties not found for this id: " + seriesPropertiesId));

        UserEntity loggedUser = userService.getLoggedUser();
        if (!(loggedUser.equals(seriesProperties.getUser()) || loggedUser.isAdmin())) {
            throw new ForbiddenException("No permission to open this series properties");
        }

        seriesPropertiesService.readFile(seriesProperties.getDataSeriesFile().getDataSeriesFileId(), seriesProperties);

        logger.info("Get series properties successfully completed. Id: {}", seriesPropertiesId);
        return new ResponseEntity<>(seriesPropertiesMapper.bulidSeriesPropertiesDTO(seriesProperties), HttpStatus.OK);
    }

    @DeleteMapping(produces = "application/json", value = "/{seriesPropertiesId}")
    public ResponseEntity<ResponseMessage> deletedSeriesProperties(@PathVariable(value = "seriesPropertiesId") Long seriesPropertiesId) throws ResourceNotFoundException {
        SeriesPropertiesEntity seriesProperties = seriesPropertiesService.findById(seriesPropertiesId)
                .orElseThrow(() -> new ResourceNotFoundException("SeriesProperties not found for this id: " + seriesPropertiesId));

        this.seriesPropertiesService.delete(seriesProperties);

        logger.info("Deleted series properties with id: {}", seriesPropertiesId);
        return ResponseEntity.ok(new ResponseMessage("Deleted series properties with id: " + seriesPropertiesId));
    }


}
