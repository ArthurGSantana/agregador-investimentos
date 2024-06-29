package tech.arthur.agregadorinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.arthur.agregadorinvestimentos.model.dto.AccountDto;
import tech.arthur.agregadorinvestimentos.model.dto.AccountResponseDto;
import tech.arthur.agregadorinvestimentos.model.dto.AccountStockDto;
import tech.arthur.agregadorinvestimentos.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable String id) {
        var account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> createAccount(@PathVariable String userId, @RequestBody AccountDto accountDto) {
        accountService.createAccount(accountDto, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/associateStock")
    public ResponseEntity<Void> associateStock(@RequestBody AccountStockDto accountStockDto) {
        accountService.associateStock(accountStockDto);
        return ResponseEntity.ok().build();
    }
}
