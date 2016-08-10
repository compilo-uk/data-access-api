package com.sharpe.capital.connection.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.datastax.driver.mapping.Mapper;
import com.sharpe.capital.data.access.api.enums.CqlStatement;
import com.sharpe.capital.data.access.api.manager.CassandraManager;
import com.sharpe.capital.data.access.api.model.cassandra.FxTick;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CassandraManagerTest {

	private static final int PORT = 9042;
	private static final String[] HOSTS = { "127.0.0.1" };

	private static final ExecutorService executor = Executors.newCachedThreadPool();
	private static final CassandraManager connectionManager = CassandraManager.getInstance(HOSTS, PORT);

	@BeforeClass
	public static void setup() {
		truncateFxTicks();
	}

	@AfterClass
	public static void cleanup() {
		connectionManager.close();
	}

	@Test
	public void stage1_testAddQuote() throws InterruptedException {

		Mapper<FxTick> fxTickMapper = connectionManager.getMapper(FxTick.class);

		long startCount = countFxTicks();

		for (int i = 0; i < 10; i++) {

			FxTick quote = new FxTick("AUD/USD", new Date(), BigDecimal.valueOf(1.0D), BigDecimal.valueOf(1.0D));

			Thread.sleep(5L);

			executor.execute(() -> {
				fxTickMapper.save(quote);
			});

		}

		executor.shutdown();
		executor.awaitTermination(15, TimeUnit.SECONDS);

		long endCount = countFxTicks();

		Assert.assertTrue((endCount - startCount) == 10);

	}

	@Test
	public void stage2_testEmptyQuotes() {

		long startCount = countFxTicks();

		Assert.assertTrue(startCount > 0);

		truncateFxTicks();

		long endCount = countFxTicks();

		Assert.assertTrue(endCount == 0);

	}

	private static long countFxTicks() {
		return connectionManager.getOne(CqlStatement.CQL_COUNT_FX_TICKS).getLong(0);
	}

	private static void truncateFxTicks() {
		connectionManager.execute(CqlStatement.CQL_TRUNCATE_FX_TICKS, FxTick.class);
	}

}
