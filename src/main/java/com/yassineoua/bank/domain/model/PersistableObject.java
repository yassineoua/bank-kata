package com.yassineoua.bank.domain.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersistableObject implements Serializable {

    private Long id;
}
