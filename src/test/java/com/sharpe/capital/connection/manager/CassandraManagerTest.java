package com.sharpe.capital.connection.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.Executors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.datastax.driver.mapping.Mapper;
import com.sharpe.capital.data.access.api.manager.CassandraManager;
import com.sharpe.capital.data.access.api.model.cassandra.FxTick;

public class CassandraManagerTest {

	private static final int PORT = 9042;

	private static final String[] HOSTS = { "127.0.0.1" };

	private static final CassandraManager connectionManager = CassandraManager.getInstance(HOSTS, PORT);
	
	@BeforeClass
	public static void setup() {
		// TODO - Empty fx.tick table
	}
	
	@AfterClass
	public static void cleanup() {
		connectionManager.close();
	}

	@Test
	public void testAddQuote() throws InterruptedException {
		
		Mapper<FxTick> fxTickMapper = connectionManager.getMapper(FxTick.class);
		
		// TODO - count records in  fx.tick table
		
		for (int i = 0; i < 10; i++) {
			
			FxTick quote = new FxTick("AUD/USD", new Date(), BigDecimal.valueOf(1.0D), BigDecimal.valueOf(1.0D));
			
			Thread.sleep(5L);
			
			Executors.newCachedThreadPool().execute(() -> {
				fxTickMapper.save(quote);
			});
			
		}
		
		// TODO - after Executor completes count records in fx.tick table
		// TODO - Assert 10 records were added
		
	}

	@Test
	public void testEmptyQuotes() {
		// TODO - Assert more than 0 records in fx.tick table
		connectionManager.execute("TRUNCATE fx.tick", FxTick.class);
		// TODO - Assert 0 records in fx.tick table
	}

}
