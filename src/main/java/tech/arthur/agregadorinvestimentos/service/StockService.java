package tech.arthur.agregadorinvestimentos.service;

import org.springframework.stereotype.Service;
import tech.arthur.agregadorinvestimentos.entity.Stock;
import tech.arthur.agregadorinvestimentos.model.dto.StockDto;
import tech.arthur.agregadorinvestimentos.repository.StockRepository;

import java.util.List;
import java.util.UUID;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<StockDto> getAllStocks() {
        var stocks = stockRepository.findAll();
        return stocks.stream()
                .map(stock -> new StockDto(stock.getId(), stock.getTicker(), stock.getDescription()))
                .toList();
    }

    public void createStock(StockDto stockDto) {
        var stock = new Stock(
                UUID.randomUUID().toString(),
                stockDto.ticker(),
                stockDto.description()
        );

        stockRepository.save(stock);
    }
}
