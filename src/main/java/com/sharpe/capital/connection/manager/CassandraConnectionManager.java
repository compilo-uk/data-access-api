package com.sharpe.capital.connection.manager;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

public final class CassandraConnectionManager implements ConnectionManager {

	private static CassandraConnectionManager instance = null;

	private final String[] hosts;

	private final int port;

	private final Cluster cluster;

	private final Session session;

	private final MappingManager manager;

	private CassandraConnectionManager(final String[] hosts, final int port) {
		this.hosts = hosts;
		this.port = port;
		this.cluster = Cluster.builder().addContactPoints(this.hosts).withPort(this.port).build();
		this.session = this.cluster.connect();
		this.manager = new MappingManager(this.session);
	}

	/**
	 * Returns a Singleton instance of the Cassandra connection manager
	 * 
	 * @param hosts
	 *            an array of hosts in the Cassandra cluster
	 * @param port
	 *            the Cassandra connectivity port
	 * 
	 * @return Cassandra connection manager object
	 */
	public static CassandraConnectionManager getInstance(final String[] hosts, final int port) {
		if (instance == null) {
			instance = new CassandraConnectionManager(hosts, port);
		}
		return instance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		this.session.close();
		this.cluster.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> Mapper<T> getMapper(Class<T> type) {
		return this.manager.mapper(type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> Result<T> execute(String query, Class<T> type) {
		return this.getMapper(type).map(this.session.execute(query));
	}

}