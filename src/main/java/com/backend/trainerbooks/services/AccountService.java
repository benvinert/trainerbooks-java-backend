package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.AccountDAO;
import com.backend.trainerbooks.repositorys.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountDAO save(AccountDAO accountDAO) {
        return accountRepository.save(accountDAO);
    }

    public Optional<AccountDAO> findById(Long accountId) {
        return accountRepository.findById(accountId);
    }
}
