package com.nttbootcamp.msappmobileyanki.domain.enums;

public enum YankiTransactionType {
    YANKI_PAYMENT("YANKI PAYMENT"),
    YANKI_PAYMENT_DEBIT_CARD("YANKI PAYMENT WITH DEBIT CARD"),
    ACCOUNT_DEPOSIT("DEPOSIT IN ACCOUNT"),
    ACCOUNT_WITHDRAWAL("WITHDRAWAL IN ACCOUNT"),
    DEBIT_CARD_CREATION("DEBIT_CARD_CREATION"),
    DEPOSIT("DEPOSIT"),
    WITHDRAWAL("WITHDRAWAL");
    public final String type;

    YankiTransactionType(String type) {
        this.type = type;
    }
}
