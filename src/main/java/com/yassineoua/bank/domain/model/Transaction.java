package com.yassineoua.bank.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class Transaction extends PersistableObject {

    private TransactionType type;

    private StatementType statementType;

    private TransactionStatus status;

    private BankAccount account;

    private BigDecimal amount;

    private BigDecimal balance;

    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();
}
