package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.SeriesPropertiesMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.DataSeriesFileEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.SeriesPropertiesDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.DataSeriesFileService;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.SeriesPropertiesService;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.UserService;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/seriesProperties")
public class SeriesPropertiesController {
    private Logger logger = LoggerFactory.getLogger(SeriesPropertiesController.class);

    private SeriesPropertiesService seriesPropertiesService;
    private DataSeriesFileService dataSeriesFileService;
    private UserService userService;
    private SeriesPropertiesMapper seriesPropertiesMapper;

    public SeriesPropertiesController(SeriesPropertiesService seriesPropertiesService,
                                      DataSeriesFileService dataSeriesFileService, UserService userService,
                                      SeriesPropertiesMapper seriesPropertiesMapper) {
        this.seriesPropertiesService = seriesPropertiesService;
        this.dataSeriesFileService = dataSeriesFileService;
        this.userService = userService;
        this.seriesPropertiesMapper = seriesPropertiesMapper;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<SeriesPropertiesDTO> postSeriesProperties(@RequestParam("dataSeriesFileId") long dataSeriesFileId, @RequestParam("precision") int precision) {

        SeriesPropertiesEntity seriesProperties = new SeriesPropertiesEntity();
        Optional<DataSeriesFileEntity> dataSeriesFileEntity = dataSeriesFileService.findById(dataSeriesFileId);
        UserEntity userEntity = userService.getLoggedUser();

        if (dataSeriesFileEntity.isPresent()) {
            seriesProperties.setDataSeriesFile(dataSeriesFileEntity.get());
            seriesProperties.setDeleted((byte) 0);
            seriesProperties.setUser(userEntity);
            seriesProperties.setPrecisionApproximation(precision);

            seriesPropertiesService.readFile(dataSeriesFileId, seriesProperties);
            seriesPropertiesService.propertiesCalculate(seriesProperties);
            seriesProperties = seriesPropertiesService.save(seriesProperties);

            return new ResponseEntity<>(seriesPropertiesMapper.bulidSeriesPropertiesDTO(seriesProperties), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SeriesPropertiesDTO>> getSeriesPropertiesUser() {
        UserEntity userEntity = userService.getLoggedUser();

        List<SeriesPropertiesEntity> seriesPropertiesEntities = seriesPropertiesService.findByUserAndDeleted(userEntity, (byte) 0);
        List<SeriesPropertiesDTO> seriesPropertiesDTOs = seriesPropertiesMapper.bulidSeriesPropertiesDTOs(seriesPropertiesEntities);

        return new ResponseEntity<>(seriesPropertiesDTOs, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/all")
    public ResponseEntity<List<SeriesPropertiesDTO>> getSeriesPropertiesAdmin() {
        List<SeriesPropertiesEntity> seriesPropertiesEntities = seriesPropertiesService.findAll();
        List<SeriesPropertiesDTO> seriesPropertiesDTOs = seriesPropertiesMapper.bulidSeriesPropertiesDTOs(seriesPropertiesEntities);

        return new ResponseEntity<>(seriesPropertiesDTOs, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/{seriesPropertiesId}")
    public ResponseEntity<SeriesPropertiesDTO> getSeriesProperties(@PathVariable long seriesPropertiesId){
        Optional<SeriesPropertiesEntity> seriesPropertiesOptional = seriesPropertiesService.findById(seriesPropertiesId);
        if(seriesPropertiesOptional.isPresent()){
            //TODO
            //Walidacja użytkownika

            SeriesPropertiesEntity seriesProperties = seriesPropertiesOptional.get();
            seriesPropertiesService.readFile(seriesProperties.getDataSeriesFile().getDataSeriesFileId(), seriesProperties);
            return new ResponseEntity<>(seriesPropertiesMapper.bulidSeriesPropertiesDTO(seriesProperties), HttpStatus.OK);
        }  else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }



    }


}
