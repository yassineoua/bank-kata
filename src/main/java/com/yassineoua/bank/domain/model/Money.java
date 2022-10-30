package com.yassineoua.bank.domain.model;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

@Value
public class Money {

    private BigDecimal amount;

    private Currency currency = Currency.getInstance(Locale.getDefault());
}
