package com.lucasnscr.creditscore.controller;

import com.lucasnscr.creditscore.tools.UserExternalCreditTool;
import com.lucasnscr.creditscore.tools.UserInternalCreditTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit-score")
public class CreditScoreController {

    private static final Logger logger = LoggerFactory.getLogger(CreditScoreController.class);

    private final ChatClient chatClient;
    private final UserInternalCreditTool userInternalCreditTool;
    private final UserExternalCreditTool userExternalCreditTool;

    public CreditScoreController(ChatClient.Builder chatClientBuilder,
                                 UserInternalCreditTool userInternalCreditTool,
                                 UserExternalCreditTool userExternalCreditTool,
                                 ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                .defaultTools(tools)
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
        this.userInternalCreditTool = userInternalCreditTool;
        this.userExternalCreditTool = userExternalCreditTool;
    }

    @GetMapping("/{userId}")
    String calculateWalletValueWithTools(@PathVariable int userId) {
        logger.info("Receive Request with userId:{}", userId);
        UserMessage userMessage = new UserMessage(
                """
                        Provide a credit analyse of specific customer userId: %s.
                        The summary should include the following sections:
                       \s
                        Steps for Analysis:
                        ## 1. Risk Calculation:
                        	- Analyze the user’s financial history based on the available data.
                        	- Determine the probability of default based on past scores.
                        	- Identify payment behavior patterns that may influence the decision.
                       \s
                        ## 2. Credit Score Average Calculation:
                        	- Calculate the average credit score for the user to understand their financial consistency over time.
                       \s
                        ## 3. Decision and Recommendation:
                        	- Based on the calculations, provide a recommended decision:
                        	- Approved: The user has a low risk and can receive credit.
                        	- Denied: The user presents a high risk, and granting credit is not recommended.
                        	- Suggest additional measures to reduce risk (e.g., lower credit limit, collateral, co-signer, etc.).
                      \s
                        ## **4. Formatting for Slack:**
                           - Present the information in a structured and well-organized format
                           - Use **bold headings** (`*Decision and Recommendation*`)
                           - Post the summary to the **'#all-ai-agent-alerts'** Slack channel \t
                          \s
                        The summary should be concise, clear, and actionable to help the credit team quickly understand what decision we have to take.
                        \t
                       \s""".formatted(userId));

        return chatClient.prompt(new Prompt(userMessage))
                .tools(userInternalCreditTool, userExternalCreditTool)
                .call()
                .content();
    }
}