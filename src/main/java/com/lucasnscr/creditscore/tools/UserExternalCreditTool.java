package com.lucasnscr.creditscore.tools;

import com.lucasnscr.creditscore.client.ExternalBureauClient;
import com.lucasnscr.creditscore.record.CreditScore;
import com.lucasnscr.creditscore.record.UserCreditResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.List;

public class UserExternalCreditTool {

    private static final Logger logger = LoggerFactory.getLogger(UserExternalCreditTool.class);

    private final ExternalBureauClient externalBureauClient;

    public UserExternalCreditTool(ExternalBureauClient externalBureauClient) {
        this.externalBureauClient = externalBureauClient;
    }

    @Tool(description = "Get user credit score from external bureau mock")
    public UserCreditResponse requestExternalBureauMock(@ToolParam(description = "User credit score Id") int userId) {
        CreditScore creditScore = externalBureauClient.requestBureau(userId);
        logger.info("Got user credit score from external bureau mock");
        return new UserCreditResponse(getCreditScore(creditScore));
    }

    private List<CreditScore> getCreditScore(CreditScore creditScore) {
        return List.of(new CreditScore(creditScore.bureau(), creditScore.score()));
    }
}
