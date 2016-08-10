package com.sharpe.capital.data.access.api.enums;

public final class CqlStatement {

	private CqlStatement() {
	}

	/**
	 * SELECT COUNT(*) FROM fx.tick
	 */
	public static final String CQL_COUNT_FX_TICKS = "SELECT COUNT(*) FROM fx.tick";

	/**
	 * TRUNCATE fx.tick
	 */
	public static final String CQL_TRUNCATE_FX_TICKS = "TRUNCATE fx.tick";

}