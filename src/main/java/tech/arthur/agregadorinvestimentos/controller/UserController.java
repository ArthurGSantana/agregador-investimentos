package tech.arthur.agregadorinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.arthur.agregadorinvestimentos.entity.User;
import tech.arthur.agregadorinvestimentos.model.dto.UserDTO;
import tech.arthur.agregadorinvestimentos.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable String id) {
        var user = userService.findUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        var user = userService.createUser(userDTO);
        return ResponseEntity.ok().body(user);
    }
}
