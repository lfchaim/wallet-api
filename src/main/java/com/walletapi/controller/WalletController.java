package com.walletapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.walletapi.model.Wallet;
import com.walletapi.service.WalletService;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<Wallet> createWallet(@RequestParam String userId) {
        Wallet wallet = walletService.createWallet(userId);
        return ResponseEntity.ok(wallet);
    }

    @GetMapping("/{userId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable String userId) {
        double balance = walletService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/{userId}/deposit")
    public ResponseEntity<Wallet> depositFunds(@PathVariable String userId, @RequestParam double amount) {
        Wallet wallet = walletService.depositFunds(userId, amount);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/{userId}/withdraw")
    public ResponseEntity<Wallet> withdrawFunds(@PathVariable String userId, @RequestParam double amount) {
        Wallet wallet = walletService.withdrawFunds(userId, amount);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestParam String fromUserId, @RequestParam String toUserId, @RequestParam double amount) {
        walletService.transferFunds(fromUserId, toUserId, amount);
        return ResponseEntity.ok("Transfer successful");
    }
}