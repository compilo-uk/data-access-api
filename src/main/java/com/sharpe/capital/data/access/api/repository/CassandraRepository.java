package com.sharpe.capital.data.access.api.repository;

/**
 * This class is extended by all Cassandra repositories and exposes the cluster
 * connection details (to be added to property file later...)
 */
public abstract class CassandraRepository {

	/**
	 * Connectivity port of cluster
	 */
	protected static final int PORT = 9042;

	/**
	 * Cassandra hosts in cluster
	 */
	protected static final String[] HOSTS = { "127.0.0.1" };

}