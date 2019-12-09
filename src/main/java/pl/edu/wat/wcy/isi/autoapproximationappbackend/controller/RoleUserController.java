package pl.edu.wat.wcy.isi.autoapproximationappbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.dto.RoleUserDTO;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.mapper.RoleUserMapper;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.model.entityModels.RoleUserEntity;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.service.RoleUserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/roleUser")
public class RoleUserController {
    private RoleUserService roleUserService;
    private RoleUserMapper roleUserMapper;

    public RoleUserController(RoleUserService roleUserService, RoleUserMapper roleUserMapper) {
        this.roleUserService = roleUserService;
        this.roleUserMapper = roleUserMapper;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<RoleUserDTO>> getAllUserRoles() {
        List<RoleUserEntity> roleUserEntities = roleUserService.getAll();
        List<RoleUserDTO> roleUserDTOs = roleUserMapper.buildRoleUserDTO(roleUserEntities);

        return new ResponseEntity<>(roleUserDTOs, HttpStatus.OK);
    }
}
