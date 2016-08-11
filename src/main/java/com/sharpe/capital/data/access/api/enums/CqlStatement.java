package com.sharpe.capital.data.access.api.enums;

/**
 * Defines all Native CQL statements to be used throughout the application
 */
public final class CqlStatement {

	/**
	 * Private Constructor restricts use of this class to static context only
	 */
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