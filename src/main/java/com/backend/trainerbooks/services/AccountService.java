package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.AccountDAO;
import com.backend.trainerbooks.repositorys.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountDAO save(AccountDAO accountDAO) {
        return accountRepository.save(accountDAO);
    }
}
