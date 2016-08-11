package com.sharpe.capital.data.access.api.repository;

import com.sharpe.capital.data.access.api.manager.CassandraManager;
import com.sharpe.capital.data.access.api.model.cassandra.FxTick;

/**
 * Exposes methods for accessing fx.tick table in Cassandra persistence store
 */
public class FxTickRepository extends CassandraRepository {

	private static FxTickRepository instance = null;

	private static final CassandraManager connectionManager = CassandraManager.getInstance(HOSTS, PORT);

	/**
	 * Private Constructor restricts access to Singleton & static context
	 */
	private FxTickRepository() {
	}

	/**
	 * Creates a Singleton intance of the FxTickRepository class
	 * 
	 * @return the FxTickRepository object instance
	 */
	public static synchronized FxTickRepository getInstance() {
		if (instance == null) {
			instance = new FxTickRepository();
		}
		return instance;
	}

	/**
	 * Persists an FxTick object in the underlying Cassandra data store
	 * 
	 * @param fxTick
	 *            the FxTick object for persistence
	 */
	public void save(FxTick fxTick) {
		connectionManager.getMapper(FxTick.class).save(fxTick);
	}

}