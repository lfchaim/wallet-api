package com.walletapi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.walletapi.model.Wallet;
import com.walletapi.model.WalletEvent;

public interface WalletEventRepository extends MongoRepository<WalletEvent, String> {
    
	Optional<Wallet> findByWalletId(String walletId);
	
}