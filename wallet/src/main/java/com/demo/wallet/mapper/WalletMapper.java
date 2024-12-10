package com.demo.wallet.mapper;

import com.demo.wallet.dto.WalletDto;
import com.demo.wallet.entity.Wallet;

public class WalletMapper {

    public static WalletDto mapToWalletDto(Wallet wallet, WalletDto walletDto) {
        walletDto.setWalletNumber(wallet.getWalletNumber());
        walletDto.setBalance(wallet.getBalance());
        return walletDto;
    }

    public static Wallet mapToWallet(WalletDto walletDto, Wallet wallet) {
        wallet.setWalletNumber(walletDto.getWalletNumber());
        wallet.setBalance(walletDto.getBalance());
        return wallet;
    }
}
