package com.walletapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.walletapi.model.WalletEvent;

public interface WalletEventRepository extends MongoRepository<WalletEvent, String> {
    
	List<WalletEvent> findByWalletId(String walletId);
	
}