package tech.arthur.agregadorinvestimentos.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.arthur.agregadorinvestimentos.entity.Account;
import tech.arthur.agregadorinvestimentos.entity.User;
import tech.arthur.agregadorinvestimentos.model.dto.AccountListDto;
import tech.arthur.agregadorinvestimentos.model.dto.UserDto;
import tech.arthur.agregadorinvestimentos.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserById(String id) {
        var user = userRepository.findById(id);
        return user.map(value -> new UserDto(value.getId(), value.getUsername(), value.getEmail(), null)).orElse(null);
    }

    public List<UserDto> getAllUsers() {
        var users = userRepository.findAll();
        return users.stream().map(user -> new UserDto(user.getId(), user.getUsername(), user.getEmail(), null)).toList();
    }

    public List<AccountListDto> listAccounts(String userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts().stream().map(account -> new AccountListDto(account.getId(), account.getDescription())).toList();
    }

    public User createUser(UserDto userDTO) {
        var newUser = new User(UUID.randomUUID().toString(), userDTO.username(), userDTO.email(), userDTO.password().orElse(null));
        userRepository.save(newUser);

        return newUser;
    }

    public void updateUser(String id, UserDto userDTO) {
        var user = userRepository.findById(id);

        if (user.isEmpty()) {
            return;
        }

        var updatedUser = user.get();

        if (userDTO.username() != null) {
            updatedUser.setUsername(userDTO.username());
        }

        if (userDTO.password().isPresent()) {
            updatedUser.setPassword(userDTO.password().get());
        }

        userRepository.save(updatedUser);
    }

    public void deleteUser(String id) {
        var userExists = userRepository.existsById(id);

        if (!userExists) {
            return;
        }

        userRepository.deleteById(id);
    }
}
