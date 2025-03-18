package com.lucasnscr.creditscore.record;

import java.util.List;

public record UserCreditResponse(List<CreditScore> creditScores) {
}
