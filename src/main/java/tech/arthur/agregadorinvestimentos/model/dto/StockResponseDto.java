package tech.arthur.agregadorinvestimentos.model.dto;

public record StockResponseDto(
        String id,
        String ticker,
        Integer quantity,
        Double total,
        Double regularPrice
) {
}
