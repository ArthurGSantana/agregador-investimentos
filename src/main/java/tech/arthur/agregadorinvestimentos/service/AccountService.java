package tech.arthur.agregadorinvestimentos.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.arthur.agregadorinvestimentos.entity.Account;
import tech.arthur.agregadorinvestimentos.entity.BillingAddress;
import tech.arthur.agregadorinvestimentos.model.dto.AccountDto;
import tech.arthur.agregadorinvestimentos.model.dto.AccountResponseDto;
import tech.arthur.agregadorinvestimentos.model.dto.StockDto;
import tech.arthur.agregadorinvestimentos.repository.AccountRepository;
import tech.arthur.agregadorinvestimentos.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BillingAddressService billingAddressService;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, BillingAddressService billingAddressService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.billingAddressService = billingAddressService;
    }

    public AccountResponseDto getAccountById(String id) {
        return accountRepository.findById(id)
                .map(account -> new AccountResponseDto(
                        account.getDescription(),
                        account.getBillingAddress().getStreet(),
                        account.getBillingAddress().getNumber(),
                        account.getAccountStocks().stream()
                                .map(accountStock -> new StockDto(
                                        accountStock.getStock().getTicker(),
                                        accountStock.getQuantity()
                                ))
                                .toList()
                ))
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Transactional
    public void createAccount(AccountDto accountDto, String userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account(
                UUID.randomUUID().toString(),
                user,
                accountDto.description()
        );

        var newAccount = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                newAccount.getId(),
                newAccount,
                accountDto.street(),
                accountDto.number()
        );

        billingAddressService.createBillingAddress(billingAddress);
    }
}
