package com.lucasnscr.creditscore.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("USER_CREDIT")
public record UserCredit (
    @Id
    Integer id,
    float paymentHistoryScore,
    float internalCreditScore
){}

