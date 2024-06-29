package tech.arthur.agregadorinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.arthur.agregadorinvestimentos.entity.AccountStock;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, String> {
}
