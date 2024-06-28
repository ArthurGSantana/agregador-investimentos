package tech.arthur.agregadorinvestimentos.model.dto;

import java.util.List;

public record AccountResponseDto(
        String description,
        String street,
        Integer number,
        List<StockDto> stocks
) {
}
