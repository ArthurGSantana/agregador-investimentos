package tech.arthur.agregadorinvestimentos.model.dto;

import java.util.Optional;

public record UserDto(String id, String username, String email, Optional<String> password) {
}
