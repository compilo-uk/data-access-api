package com.sharpe.capital.data.access.api.model;

import java.math.BigDecimal;
import java.util.Date;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(keyspace = "fx_data", name = "tick", readConsistency = "QUORUM", writeConsistency = "QUORUM", caseSensitiveKeyspace = false, caseSensitiveTable = false)
public class Tick {

	@NonNull
	@PartitionKey
	@Column(name = "symbol")
	private String symbol;

	@NonNull
	@ClusteringColumn
	@Column(name = "date")
	private Date date;

	@NonNull
	@Column(name = "ask")
	private BigDecimal ask;

	@NonNull
	@Column(name = "bid")
	private BigDecimal bid;

}
