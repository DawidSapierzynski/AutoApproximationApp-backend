package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.DataSeriesFileDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.message.response.ResponseMessage;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.exception.ResourceNotFoundException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.DataSeriesFileMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.DataSeriesFileEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.DataSeriesFileService;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.StorageService;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.UserService;

import java.sql.Timestamp;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/dataSeriesFile")
public class DataSeriesFileController {
    private static final String FILE_EXTENSION = ".csv";

    private final Logger logger = LoggerFactory.getLogger(DataSeriesFileController.class);
    private final StorageService storageService;
    private final DataSeriesFileService dataSeriesFileService;
    private final UserService userService;
    private final DataSeriesFileMapper dataSeriesFileMapper;

    public DataSeriesFileController(StorageService storageService, DataSeriesFileService dataSeriesFileService, UserService userService, DataSeriesFileMapper dataSeriesFileMapper) {
        this.storageService = storageService;
        this.dataSeriesFileService = dataSeriesFileService;
        this.userService = userService;
        this.dataSeriesFileMapper = dataSeriesFileMapper;
    }

    @Transactional
    @PostMapping(produces = "application/json")
    public ResponseEntity<DataSeriesFileDTO> uploadFile(@RequestParam("dataSeriesFile") MultipartFile dataSeriesFile) {
        DataSeriesFileDTO dataSeriesFileDTO;
        DataSeriesFileEntity dataSeriesFileEntity = new DataSeriesFileEntity();
        UserEntity userEntity = userService.getLoggedUser();

        dataSeriesFileEntity.setDateSent(new Timestamp(System.currentTimeMillis()));
        dataSeriesFileEntity.setDeleted((byte) 0);
        dataSeriesFileEntity.setName(dataSeriesFile.getOriginalFilename());
        dataSeriesFileEntity.setHashName(RandomString.make(100));
        dataSeriesFileEntity.setUser(userEntity);

        dataSeriesFileEntity = dataSeriesFileService.save(dataSeriesFileEntity);

        storageService.store(dataSeriesFile, dataSeriesFileEntity.getDataSeriesFileId() + FILE_EXTENSION);

        dataSeriesFileDTO = dataSeriesFileMapper.buildDataSeriesFile(dataSeriesFileEntity);

        logger.info("The file was successfully added.");
        return new ResponseEntity<>(dataSeriesFileDTO, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/all")
    public ResponseEntity<List<DataSeriesFileDTO>> getAll() {
        List<DataSeriesFileEntity> dataSeriesFileEntities = dataSeriesFileService.findAll();
        List<DataSeriesFileDTO> dataSeriesFileDTOs = dataSeriesFileMapper.buildDataSeriesFiles(dataSeriesFileEntities);

        logger.info("Getting all the files successfully completed. Size: {}", dataSeriesFileDTOs.size());
        return new ResponseEntity<>(dataSeriesFileDTOs, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<DataSeriesFileDTO>> getAllForUser() {
        UserEntity userEntity = userService.getLoggedUser();

        List<DataSeriesFileEntity> dataSeriesFileEntities = dataSeriesFileService.findByUserAndDeleted(userEntity, (byte) 0);
        List<DataSeriesFileDTO> dataSeriesFileDTOs = dataSeriesFileMapper.buildDataSeriesFiles(dataSeriesFileEntities);

        logger.info("Getting all (for user) the files successfully completed. Size: {}", dataSeriesFileDTOs.size());
        return new ResponseEntity<>(dataSeriesFileDTOs, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(produces = "application/json", value = "/{dataSeriesFileId}")
    public ResponseEntity<ResponseMessage> deletedDataSeriesFile(@PathVariable(value = "dataSeriesFileId") Long dataSeriesFileId) throws ResourceNotFoundException {
        DataSeriesFileEntity dataSeriesFile = dataSeriesFileService.findById(dataSeriesFileId)
                .orElseThrow(() -> new ResourceNotFoundException("DataSeriesFileEntity not found for this id: " + dataSeriesFileId));

        this.dataSeriesFileService.delete(dataSeriesFile);

        logger.info("Deleted data series file with id: {}", dataSeriesFileId);
        return ResponseEntity.ok(new ResponseMessage("Deleted data series file with id: " + dataSeriesFileId));
    }

}
