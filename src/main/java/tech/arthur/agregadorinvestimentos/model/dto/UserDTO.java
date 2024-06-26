package tech.arthur.agregadorinvestimentos.model.dto;

import java.util.Optional;
import java.util.UUID;

public record UserDTO(String id, String username, String email, Optional<String> password) {
}
