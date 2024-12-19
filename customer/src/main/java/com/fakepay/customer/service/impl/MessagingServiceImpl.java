package com.fakepay.customer.service.impl;

import com.fakepay.customer.dto.CustomerDto;
import com.fakepay.customer.dto.WalletMsgDto;
import com.fakepay.customer.service.IMessagingService;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class MessagingServiceImpl implements IMessagingService {

    private static final Logger log = LoggerFactory.getLogger(MessagingServiceImpl.class);
    private final StreamBridge streamBridge;

    @Override
    public boolean sendCommunication(WalletMsgDto walletMsgDto){
        log.info("Sending Communication request for the details: {}", walletMsgDto);
        boolean result = streamBridge.send("sendCommunication-out-0", walletMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);

        return result;
    }
}
