package tech.arthur.agregadorinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.arthur.agregadorinvestimentos.model.dto.AccountDto;
import tech.arthur.agregadorinvestimentos.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> createAccount(@PathVariable String userId, @RequestBody AccountDto accountDto) {
        accountService.createAccount(accountDto, userId);
        return ResponseEntity.ok().build();
    }
}
