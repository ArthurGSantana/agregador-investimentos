package tech.arthur.agregadorinvestimentos.service;

import org.springframework.stereotype.Service;
import tech.arthur.agregadorinvestimentos.entity.User;
import tech.arthur.agregadorinvestimentos.model.dto.UserDTO;
import tech.arthur.agregadorinvestimentos.repository.UserRepository;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO findUserById(String id) {
        var user = userRepository.findById(id);
        return user.map(value -> new UserDTO(value.getId(), value.getUsername(), value.getEmail(), null)).orElse(null);
    }

    public User createUser(UserDTO userDTO) {
        var newUser = new User(UUID.randomUUID().toString(), userDTO.username(), userDTO.email(), userDTO.password().orElse(null));
        userRepository.save(newUser);

        return newUser;
    }
}
