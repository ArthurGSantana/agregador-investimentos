package tech.arthur.agregadorinvestimentos.service;

import org.springframework.stereotype.Service;
import tech.arthur.agregadorinvestimentos.entity.User;
import tech.arthur.agregadorinvestimentos.model.dto.UserDTO;
import tech.arthur.agregadorinvestimentos.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO GetUserById(String id) {
        var user = userRepository.findById(id);
        return user.map(value -> new UserDTO(value.getId(), value.getUsername(), value.getEmail(), null)).orElse(null);
    }

    public List<UserDTO> GetAllUsers() {
        var users = userRepository.findAll();
        return users.stream().map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail(), null)).toList();
    }

    public User createUser(UserDTO userDTO) {
        var newUser = new User(UUID.randomUUID().toString(), userDTO.username(), userDTO.email(), userDTO.password().orElse(null));
        userRepository.save(newUser);

        return newUser;
    }

    public void updateUser(String id, UserDTO userDTO) {
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
