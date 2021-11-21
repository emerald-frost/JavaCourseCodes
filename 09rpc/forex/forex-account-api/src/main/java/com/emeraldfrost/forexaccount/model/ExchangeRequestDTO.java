package com.emeraldfrost.forexaccount.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExchangeRequestDTO implements Serializable {

	private static final long serialVersionUID = -678163388925693506L;

	private String username;

	private String sourceCurrency;

	private BigDecimal amount;

	private String destCurrency;
}
