package tech.arthur.agregadorinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.arthur.agregadorinvestimentos.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}
