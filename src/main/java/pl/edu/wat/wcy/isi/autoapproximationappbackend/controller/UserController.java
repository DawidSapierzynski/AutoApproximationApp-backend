package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.UserDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.exception.ResourceNotFoundException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.UserMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.message.response.ResponseMessage;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserEntity> userEntities = userService.getAll();
        List<UserDTO> userDTOs = userMapper.buildUserDTOs(userEntities);

        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @DeleteMapping(produces = "application/json", value = "/{userId}")
    public ResponseEntity<ResponseMessage> deletedUser(@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
        UserEntity user = userService.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id ::" + userId));

        this.userService.delete(user);

        return ResponseEntity.ok(new ResponseMessage("Deleted user with id: " + userId));
    }
}
