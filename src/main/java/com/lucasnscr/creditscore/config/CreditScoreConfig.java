package com.lucasnscr.creditscore.config;

import com.lucasnscr.creditscore.client.ExternalBureauClient;
import com.lucasnscr.creditscore.repository.UserCreditRepository;
import com.lucasnscr.creditscore.tools.UserExternalCreditTool;
import com.lucasnscr.creditscore.tools.UserInternalCreditTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreditScoreConfig {

    public static final String GET_USER_EXTERNAL_CREDIT = "getuserExternalCredit";
    public static final String GET_USER_INTERNAL_CREDIT = "getuserInternalCredit";

    @Bean(name = GET_USER_EXTERNAL_CREDIT)
    public UserExternalCreditTool externalCreditTool(ExternalBureauClient externalBureauClient) {
        return new UserExternalCreditTool(externalBureauClient);
    }

    @Bean(name = GET_USER_INTERNAL_CREDIT)
    public UserInternalCreditTool userInternalCreditTool(UserCreditRepository userCreditRepository) {
        return new UserInternalCreditTool(userCreditRepository);
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools) {
        return chatClientBuilder
                .defaultTools(tools)
                .build();
    }
}