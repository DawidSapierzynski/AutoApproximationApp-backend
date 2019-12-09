package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.jwt.JwtProvider;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.RoleUserMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.UserMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.message.request.LoginForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.message.request.SignUpForm;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.message.response.JwtResponse;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.message.response.ResponseMessage;
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
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping(produces = "application/json", value = "/signup")
    public ResponseEntity<ResponseMessage> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userService.existsByLogin(signUpRequest.getLogin())) {
            return new ResponseEntity<>(new ResponseMessage("Fail - Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail - Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userMapper.buildUserEntity(signUpRequest);

        user = userService.save(user);
        roleUserToUserService.addRoleToUser(user, roleUserMapper.buildRoleUserEntity(signUpRequest.getRole()));

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }
}
