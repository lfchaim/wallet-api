package com.walletapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walletapi.model.Wallet;
import com.walletapi.model.WalletEvent;
import com.walletapi.repository.WalletEventRepository;
import com.walletapi.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
    private WalletRepository walletRepository;
	
	@Autowired
    private WalletEventRepository walleteventRepository;

    public Wallet createWallet(String userId) {
        Wallet wallet = new Wallet(userId);
        return walletRepository.save(wallet);
    }

    public Optional<Wallet> getWalletByUserId(String userId) {
        return walletRepository.findByUserId(userId);
    }

    public double getBalance(String userId) {
        return getWalletByUserId(userId).map(Wallet::getBalance).orElse(0.0);
    }

    public List<WalletEvent> getStatement(String userId) {
    	List<WalletEvent> listEvent = null;
    	if( userId == null ) {
    		listEvent = walleteventRepository.findAll();
    	} else {
    		Wallet wallet = getWalletByUserId(userId).orElseThrow(() -> new RuntimeException("Wallet not found"));
    		listEvent = walleteventRepository.findByWalletId(wallet.getId());
    	}
    	return listEvent;
    }
    
    public Wallet depositFunds(String userId, double amount) {
        Wallet wallet = getWalletByUserId(userId).orElseThrow(() -> new RuntimeException("Wallet not found"));
        
        WalletEvent event = new WalletEvent(wallet.getId(), amount);
        walleteventRepository.save(event);

        wallet.setBalance(wallet.getBalance() + amount);
        return walletRepository.save(wallet);
    }

    public Wallet withdrawFunds(String userId, double amount) {
        Wallet wallet = getWalletByUserId(userId).orElseThrow(() -> new RuntimeException("Wallet not found"));
        
        WalletEvent event = new WalletEvent(wallet.getId(), -amount);
        walleteventRepository.save(event);
        
        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        return walletRepository.save(wallet);
    }

    public void transferFunds(String fromUserId, String toUserId, double amount) {
        withdrawFunds(fromUserId, amount);
        depositFunds(toUserId, amount);
    }
}