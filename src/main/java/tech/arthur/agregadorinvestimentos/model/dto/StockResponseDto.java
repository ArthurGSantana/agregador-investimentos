package tech.arthur.agregadorinvestimentos.model.dto;

public record StockResponseDto(
        String id,
        String ticker,
        Integer quantity
) {
}
