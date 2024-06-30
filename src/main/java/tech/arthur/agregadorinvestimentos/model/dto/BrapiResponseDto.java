package tech.arthur.agregadorinvestimentos.model.dto;

import java.util.List;

public record BrapiResponseDto(List<BrapiStockDto> results) {
}
