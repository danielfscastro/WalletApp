package com.fakepay.wallet.service.impl;

import com.fakepay.wallet.dto.WalletDto;
import com.fakepay.wallet.entity.Wallet;
import com.fakepay.wallet.exception.ResourceNotFoundException;
import com.fakepay.wallet.mapper.WalletMapper;
import com.fakepay.wallet.repository.WalletRepository;
import com.fakepay.wallet.service.IWalletService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements IWalletService {

    private static final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);

    private WalletRepository walletRepository;

    @Override
    public void create(WalletDto walletDto) {

        /**
         * TODO: create a client to validate if customer exists.
         */

        Wallet wallet = WalletMapper.mapToWallet(walletDto, new Wallet());

        Wallet savedWallet = walletRepository.save(wallet);
    }

    @Override
    public WalletDto fetchWallet(String document) {
        Wallet wallet = walletRepository.findByDocument(document).orElseThrow(
                () -> new ResourceNotFoundException("Wallet", "document", String.valueOf(document))
        );

        return WalletMapper.mapToWalletDto(wallet, new WalletDto());
    }
}
