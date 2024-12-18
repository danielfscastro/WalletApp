package com.demo.customer.mapper;

import com.demo.customer.dto.WalletDto;
import com.demo.customer.entity.Wallet;

public class WalletMapper {

    public static WalletDto mapToWalletDto(Wallet wallet, WalletDto walletDto) {
        walletDto.setCustomerNumber(wallet.getCustomerNumber());
        walletDto.setBalance(wallet.getBalance());
        return walletDto;
    }

    public static Wallet mapToWallet(WalletDto walletDto, Wallet wallet) {
        wallet.setCustomerNumber(walletDto.getCustomerNumber());
        wallet.setBalance(walletDto.getBalance());
        return wallet;
    }
}
