package tech.arthur.agregadorinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.arthur.agregadorinvestimentos.entity.User;
import tech.arthur.agregadorinvestimentos.model.dto.AccountListDto;
import tech.arthur.agregadorinvestimentos.model.dto.UserDto;
import tech.arthur.agregadorinvestimentos.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        var user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountListDto>> listAccounts(@PathVariable String userId) {
        var accounts = userService.listAccounts(userId);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDTO) {
        var user = userService.createUser(userDTO);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable String id, @RequestBody UserDto userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
