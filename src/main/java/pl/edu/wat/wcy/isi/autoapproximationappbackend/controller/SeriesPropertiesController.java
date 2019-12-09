package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.security.UserPrinciple;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.DataSeriesFileEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.SeriesPropertiesEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.SeriesPropertiesDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.DataSeriesFileService;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.SeriesPropertiesService;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.UserService;

import java.util.ArrayList;
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

    public SeriesPropertiesController(SeriesPropertiesService seriesPropertiesService,
                                      DataSeriesFileService dataSeriesFileService, UserService userService) {
        this.seriesPropertiesService = seriesPropertiesService;
        this.dataSeriesFileService = dataSeriesFileService;
        this.userService = userService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<SeriesPropertiesDTO> postSeriesProperties(@RequestParam("dataSeriesFileId") long dataSeriesFileId, @RequestParam("precision") int precision) {

        SeriesPropertiesEntity seriesProperties = new SeriesPropertiesEntity();
        Optional<DataSeriesFileEntity> dataSeriesFileEntity = dataSeriesFileService.findById(dataSeriesFileId);
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userService.findById(userPrinciple.getId());

        if (dataSeriesFileEntity.isPresent()) {
            seriesProperties.setDataSeriesFile(dataSeriesFileEntity.get());
            seriesProperties.setDeleted((byte) 0);
            seriesProperties.setUser(userEntity);

            seriesPropertiesService.readFileForm(dataSeriesFileId, precision, seriesProperties);
            seriesPropertiesService.propertiesCalculate(seriesProperties);
            seriesProperties = seriesPropertiesService.save(seriesProperties);

            return new ResponseEntity<>(SeriesPropertiesDTO.bulid(seriesProperties), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SeriesPropertiesDTO>> getSeriesPropertiesUser() {
        UserEntity userEntity = userService.getLoggedUser();

        List<SeriesPropertiesEntity> seriesPropertiesEntities = seriesPropertiesService.findByUserAndDeleted(userEntity, (byte) 0);
        List<SeriesPropertiesDTO> seriesPropertiesDTOs = new ArrayList<>();

        //TODO
        //mapper
        for (SeriesPropertiesEntity s : seriesPropertiesEntities) {
            seriesPropertiesDTOs.add(SeriesPropertiesDTO.bulid(s));
        }

        return new ResponseEntity<>(seriesPropertiesDTOs, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/all")
    public ResponseEntity<List<SeriesPropertiesDTO>> getSeriesPropertiesAdmin() {
        List<SeriesPropertiesEntity> seriesPropertiesEntities = seriesPropertiesService.findAll();
        List<SeriesPropertiesDTO> seriesPropertiesDTOs = new ArrayList<>();

        //TODO
        //mapper
        for (SeriesPropertiesEntity s : seriesPropertiesEntities) {
            seriesPropertiesDTOs.add(SeriesPropertiesDTO.bulid(s));
        }

        return new ResponseEntity<>(seriesPropertiesDTOs, HttpStatus.OK);
    }
}
