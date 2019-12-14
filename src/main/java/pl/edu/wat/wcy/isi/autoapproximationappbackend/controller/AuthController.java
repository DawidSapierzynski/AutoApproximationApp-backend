package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.jwt.JwtProvider;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.security.UserPrinciple;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.message.request.LoginForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.message.request.SignUpForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.message.response.JwtResponse;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.message.response.ResponseMessage;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.exception.LoginException;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.RoleUserMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.UserMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.UserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.RoleUserToUserService;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.UserService;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtProvider jwtProvider;
    private UserMapper userMapper;
    private RoleUserMapper roleUserMapper;
    private RoleUserToUserService roleUserToUserService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtProvider jwtProvider,
                          UserMapper userMapper, RoleUserMapper roleUserMapper, RoleUserToUserService roleUserToUserService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.userMapper = userMapper;
        this.roleUserMapper = roleUserMapper;
        this.roleUserToUserService = roleUserToUserService;
    }

    @PostMapping(produces = "application/json", value = "/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), String.valueOf(userDetails.getId()), userDetails.getAuthorities()));
    }

    @PostMapping(produces = "application/json", value = "/signup")
    public ResponseEntity<ResponseMessage> registerUser(@Valid @RequestBody SignUpForm signUpRequest) throws LoginException {
        if (userService.existsByLogin(signUpRequest.getLogin())) {
            throw new LoginException("Fail - Username is already taken!");
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new LoginException("Fail - Email is already in use!");
        }

        UserEntity user = userMapper.buildUserEntity(signUpRequest);

        user = userService.save(user);
        roleUserToUserService.addRoleToUser(user, roleUserMapper.mapRoleUserEntities(signUpRequest.getRole()));

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }
}
