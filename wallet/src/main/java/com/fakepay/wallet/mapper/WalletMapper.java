package com.fakepay.wallet.mapper;

import com.fakepay.wallet.dto.WalletDto;
import com.fakepay.wallet.dto.WalletMsgDto;
import com.fakepay.wallet.entity.Wallet;

import java.math.BigDecimal;

public class WalletMapper {

    public static WalletDto mapToWalletDto(Wallet wallet, WalletDto walletDto) {
        walletDto.setDocument(wallet.getDocument());
        walletDto.setBalance(wallet.getBalance());
        return walletDto;
    }

    public static Wallet mapToWallet(WalletDto walletDto, Wallet wallet) {
        wallet.setDocument(walletDto.getDocument());
        wallet.setBalance(walletDto.getBalance());
        return wallet;
    }

    public static WalletDto mapToWalletDto(WalletMsgDto walletMsgDto, WalletDto walletDto) {
        walletDto.setDocument(walletMsgDto.document());
        walletDto.setBalance(BigDecimal.ZERO);
        return walletDto;
    }
}
