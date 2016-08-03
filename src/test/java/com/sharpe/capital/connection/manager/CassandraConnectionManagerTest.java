package com.sharpe.capital.connection.manager;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.sharpe.capital.data.access.api.manager.CassandraConnectionManager;
import com.sharpe.capital.data.access.api.manager.ConnectionManager;
import com.sharpe.capital.data.access.api.model.Quote;

public class CassandraConnectionManagerTest {

	private static final int PORT = 9042;

	private static final String[] HOSTS = { "127.0.0.1" };

	private static final ConnectionManager connectionManager = CassandraConnectionManager.getInstance(HOSTS, PORT);

	private static final Quote quote = new Quote("AUD/USD", new Date(), BigDecimal.valueOf(1.0D),
			BigDecimal.valueOf(1.0D));

	@Test
	public void testAddQuote() {
		connectionManager.getMapper(Quote.class).save(quote);
	}

	@Test
	public void testEmptyQuotes() {
		connectionManager.execute("TRUNCATE fx_rate.quote", Quote.class);
	}

}