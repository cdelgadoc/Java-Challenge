package com.leniolabs.challenge.service;

import java.util.Optional;

import com.leniolabs.challenge.model.Account;

public interface AccounServiceIF {

    public String createAccount(Account account);

    Optional<Account> findById(String id);
    
}
