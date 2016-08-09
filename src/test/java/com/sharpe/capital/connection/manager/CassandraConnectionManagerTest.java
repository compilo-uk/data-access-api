package com.sharpe.capital.connection.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.datastax.driver.mapping.Mapper;
import com.sharpe.capital.data.access.api.manager.CassandraConnectionManager;
import com.sharpe.capital.data.access.api.manager.ConnectionManager;
import com.sharpe.capital.data.access.api.model.cassandra.FxTick;

public class CassandraConnectionManagerTest {

	private static final int PORT = 9042;

	private static final String[] HOSTS = { "127.0.0.1" };

	private static final ConnectionManager connectionManager = CassandraConnectionManager.getInstance(HOSTS, PORT);

	private static final FxTick quote = new FxTick("AUD/USD", new Date(), BigDecimal.valueOf(1.0D),
			BigDecimal.valueOf(1.0D));

	@Test
	public void testAddQuote() {
		Mapper<FxTick> fxTickMapper = connectionManager.getMapper(FxTick.class);
		for (int i = 0; i < 10; i++) {
			Executors.newCachedThreadPool().execute(() -> {
				fxTickMapper.save(quote);
			});
		}
	}

	@Test
	public void testEmptyQuotes() {
		connectionManager.execute("TRUNCATE fx.tick", FxTick.class);
	}

}