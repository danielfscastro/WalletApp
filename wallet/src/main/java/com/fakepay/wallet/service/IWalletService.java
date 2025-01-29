package com.fakepay.wallet.service;

import com.fakepay.wallet.dto.WalletDto;

public interface IWalletService {
    public void create(WalletDto wallet);

    public WalletDto fetchWallet(String document);

}
