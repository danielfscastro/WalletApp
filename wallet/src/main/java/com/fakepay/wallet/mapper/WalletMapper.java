package com.fakepay.wallet.mapper;

import com.fakepay.wallet.dto.WalletDto;
import com.fakepay.wallet.entity.Wallet;

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
