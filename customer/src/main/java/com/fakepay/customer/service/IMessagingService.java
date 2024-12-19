package com.fakepay.customer.service;

import com.fakepay.customer.dto.WalletMsgDto;

public interface IMessagingService {
    boolean sendCommunication(WalletMsgDto walletMsgDto);
}
