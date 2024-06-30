package tech.arthur.agregadorinvestimentos.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.arthur.agregadorinvestimentos.client.BrapiClient;
import tech.arthur.agregadorinvestimentos.entity.Account;
import tech.arthur.agregadorinvestimentos.entity.AccountStock;
import tech.arthur.agregadorinvestimentos.entity.BillingAddress;
import tech.arthur.agregadorinvestimentos.entity.id.AccountStockId;
import tech.arthur.agregadorinvestimentos.model.dto.AccountDto;
import tech.arthur.agregadorinvestimentos.model.dto.AccountResponseDto;
import tech.arthur.agregadorinvestimentos.model.dto.AccountStockDto;
import tech.arthur.agregadorinvestimentos.model.dto.StockResponseDto;
import tech.arthur.agregadorinvestimentos.repository.AccountRepository;
import tech.arthur.agregadorinvestimentos.repository.AccountStockRepository;
import tech.arthur.agregadorinvestimentos.repository.StockRepository;
import tech.arthur.agregadorinvestimentos.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BillingAddressService billingAddressService;
    private final AccountStockRepository accountStockRepository;
    private final StockRepository stockRepository;
    private final BrapiClient brapiClient;

    private Map<String, Double> stockPrices;

    @Value("${TOKEN_BRAPI}")
    private String TOKEN_BRAPI;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, BillingAddressService billingAddressService, AccountStockRepository accountStockRepository, StockRepository stockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.billingAddressService = billingAddressService;
        this.accountStockRepository = accountStockRepository;
        this.stockRepository = stockRepository;
        this.brapiClient = brapiClient;

        this.stockPrices = new HashMap<>();
    }

    public AccountResponseDto getAccountById(String id) {
        return accountRepository.findById(id)
                .map(account -> new AccountResponseDto(
                        account.getId(),
                        account.getDescription(),
                        account.getBillingAddress().getStreet(),
                        account.getBillingAddress().getNumber(),
                        account.getAccountStocks().stream()
                                .map(accountStock -> new StockResponseDto(
                                        accountStock.getStock().getId(),
                                        accountStock.getStock().getTicker(),
                                        accountStock.getQuantity(),
                                        getTotal(accountStock.getStock().getTicker(), accountStock.getQuantity()),
                                        stockPrices.get(accountStock.getStock().getTicker())
                                ))
                                .toList()
                ))
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Double getTotal(String ticker, Integer quantity) {
        var brapiResponse = brapiClient.getQuote(TOKEN_BRAPI, ticker);
        var regularMarketPrice = brapiResponse.results().getFirst().regularMarketPrice();

        stockPrices.put(ticker, regularMarketPrice);

        return regularMarketPrice * quantity;
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

    @Transactional
    public void associateStock(AccountStockDto accountStockDto) {
        var account = accountRepository.findById(accountStockDto.accountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var stock = stockRepository.findById(accountStockDto.stockId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var accountStock = new AccountStock(
                new AccountStockId(account.getId(), stock.getId()),
                account,
                stock,
                accountStockDto.quantity()
        );

        accountStockRepository.save(accountStock);
    }
}
