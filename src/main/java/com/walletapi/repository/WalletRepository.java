package com.walletapi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.walletapi.model.Wallet;

public interface WalletRepository extends MongoRepository<Wallet, String> {
    
	Optional<Wallet> findByUserId(String userId);
	
}