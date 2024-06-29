package tech.arthur.agregadorinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.arthur.agregadorinvestimentos.model.dto.StockDto;
import tech.arthur.agregadorinvestimentos.service.StockService;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<StockDto>> getAllStocks() {
        var stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody StockDto stockDto) {
        stockService.createStock(stockDto);
        return ResponseEntity.ok().build();
    }
}
