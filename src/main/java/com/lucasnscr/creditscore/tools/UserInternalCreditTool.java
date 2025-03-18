package com.lucasnscr.creditscore.tools;

import com.lucasnscr.creditscore.model.UserCredit;
import com.lucasnscr.creditscore.record.CreditScore;
import com.lucasnscr.creditscore.record.UserCreditResponse;
import com.lucasnscr.creditscore.repository.UserCreditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.List;

public class UserInternalCreditTool {

    private static final Logger logger = LoggerFactory.getLogger(UserInternalCreditTool.class);
    private static final String BUREAU_INTERNAL = "internal";

    private final UserCreditRepository userCreditRepository;

    public UserInternalCreditTool(UserCreditRepository userCreditRepository) {
        this.userCreditRepository = userCreditRepository;
    }

    @Tool(description = "Ger user credit score from internal data")
    public UserCreditResponse retrieveInternalCreditUser(@ToolParam(description = "User credit score Id")  int userId) {
        logger.info("Got user credit score from internal bureau mock");
        var creditScore = userCreditRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User credit score not found"));
        logger.info("Success retrieve internal credit score");
        return new UserCreditResponse(getCreditScore(creditScore));
    }

    private List<CreditScore> getCreditScore(UserCredit userCredit) {
        return List.of(new CreditScore(BUREAU_INTERNAL, userCredit.internalCreditScore()));
    }
}
