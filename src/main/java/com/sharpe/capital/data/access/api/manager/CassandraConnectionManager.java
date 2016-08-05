package com.sharpe.capital.data.access.api.manager;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

/**
 * Connection manager for Cassandra
 */
public final class CassandraConnectionManager {

	private static Cluster cluster = null;

	private static Session session = null;

	private static MappingManager manager = null;
	
	// TODO - we may use locking inside these methods, to apply sychronization across all methods, 
	// i.e. if thread 1 is already creating a manager, thread 2 cannot attempt to execute a query until lock is released

	public static synchronized void create(final String[] hosts, final int port) {
		if(cluster == null && session == null && manager == null) {
			cluster = Cluster.builder().addContactPoints(hosts).withPort(port).build();
			session = cluster.connect();
			manager = new MappingManager(session);
		} else {
			// TODO - log warning
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		if(session != null && cluster != null && manager != null) {
			session.close();
			cluster.close();
			session = null;
			cluster = null;
			manager = null;
		} else {
			// TODO - log error / warning?
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public static synchronized <T> Mapper<T> getMapper(Class<T> type) {
		if(manager != null) {
			return manager.mapper(type);
		} else {
			// TODO - log error / warning?
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public static synchronized <T> Result<T> execute(String query, Class<T> type) {
		if(session != null) {
			return getMapper(type).map(session.execute(query));
		} else {
			// TODO - log error / warning?
		}
	}

}
