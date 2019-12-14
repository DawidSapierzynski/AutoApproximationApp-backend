package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.UserDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.message.response.ResponseMessage;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.exception.ForbiddenException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.exception.LoginException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.exception.MessageException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.exception.ResourceNotFoundException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.RoleUserMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.UserMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.RoleUserToUserService;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private UserService userService;
    private UserMapper userMapper;
    private RoleUserMapper roleUserMapper;
    private RoleUserToUserService roleUserToUserService;

    public UserController(UserService userService, UserMapper userMapper, RoleUserMapper roleUserMapper, RoleUserToUserService roleUserToUserService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.roleUserMapper = roleUserMapper;
        this.roleUserToUserService = roleUserToUserService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserEntity> userEntities = userService.getAll();
        List<UserDTO> userDTOs = userMapper.buildUserDTOs(userEntities);

        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", value = "/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") long userId) throws ResourceNotFoundException, ForbiddenException {
        UserEntity userEntities = userService.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :" + userId));
        UserEntity loggedUser = userService.getLoggedUser();

        if (!(loggedUser.getUserId() == userId || loggedUser.isAdmin())) {
            throw new ForbiddenException("No permission to open this user details");
        }

        UserDTO userDTO = userMapper.buildUserDTO(userEntities);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping(produces = "application/json", value = "/{userId}")
    public ResponseEntity<ResponseMessage> deletedUser(@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
        UserEntity user = userService.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :" + userId));

        this.userService.delete(user);

        return ResponseEntity.ok(new ResponseMessage("Deleted user with id: " + userId));
    }

    @Transactional
    @PutMapping(produces = "application/json", value = "/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "userId") Long userId, @RequestBody UserDTO userDTO) throws ResourceNotFoundException, LoginException {
        UserEntity user = userService.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :" + userId));

        if (userService.findByEmailAndLoginNot(userDTO.getEmail(), userDTO.getLogin())) {
            throw new LoginException("Fail - Email is already in use!");
        }

        user = this.userService.update(user, userDTO);

        return new ResponseEntity<>(this.userMapper.buildUserDTO(user), HttpStatus.OK);
    }
}
