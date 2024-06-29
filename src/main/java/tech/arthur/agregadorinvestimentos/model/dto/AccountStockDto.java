package tech.arthur.agregadorinvestimentos.model.dto;

public record AccountStockDto(
        String accountId,
        String stockId,
        Integer quantity
) {
}
