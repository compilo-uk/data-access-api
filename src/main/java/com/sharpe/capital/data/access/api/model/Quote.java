package com.sharpe.capital.data.access.api.model;

import java.math.BigDecimal;
import java.util.Date;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(keyspace = "fx_rate", name = "quote", readConsistency = "QUORUM", writeConsistency = "QUORUM", caseSensitiveKeyspace = false, caseSensitiveTable = false)
public class Quote {

	@PartitionKey(0)
	@Column(name = "symbol")
	private String symbol;

	@PartitionKey(1)
	@Column(name = "date")
	private Date date;

	@Column(name = "ask")
	private BigDecimal ask;

	@Column(name = "bid")
	private BigDecimal bid;

}
