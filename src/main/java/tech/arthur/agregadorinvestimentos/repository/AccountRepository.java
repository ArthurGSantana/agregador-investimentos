package tech.arthur.agregadorinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.arthur.agregadorinvestimentos.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
}
