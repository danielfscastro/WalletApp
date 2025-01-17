package com.fakepay.wallet.functions;

import com.fakepay.wallet.dto.WalletDto;
import com.fakepay.wallet.dto.WalletMsgDto;
import com.fakepay.wallet.mapper.WalletMapper;
import com.fakepay.wallet.service.IWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class WalletFunctions {

    private static final Logger log = LoggerFactory.getLogger(WalletFunctions.class);

    @Bean
    public Consumer<WalletMsgDto> createWallet(IWalletService walletService) {
        return walletMsgDto -> {
            log.info("Creating wallet for the customerNumber : " + walletMsgDto.document());
            walletService.create(WalletMapper.mapToWalletDto(walletMsgDto, new WalletDto()));
        };
    }

}