package com.sharpe.capital.data.access.api.manager;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

/**
 * Connection manager for Cassandra
 */
public final class CassandraManager {
	
	private String[] hosts = null;
	private int port = null;

	private static Cluster cluster = null;
	private static Session session = null;
	private static MappingManager manager = null;

	private static CassandraManager instance = null;

	private CassandraManager(String[] hosts, int port) {
		this.hosts = hosts;
		this.port = port;
		this.connect(this.hosts, this.port);
	}
	
	private void connect(String[] hosts, int port) {
		cluster = Cluster.builder().addContactPoints(hosts).withPort(port).build();
		session = cluster.connect();
		manager = new MappingManager(session);
	}

	/**
 	* Returns a Singleton intance of the CassandraManager object
 	* 
 	* @param hosts an array of hosts in the Cassandra cluster
 	* @param port the Cassandra cluster connection port
 	* 
 	* @return the application-wide CassandraManager object instance
 	*/
	public static synchronized CassandraManager getInstance(final String[] hosts, final int port) {
		if (instance == null) {
			instance = new CassandraManager(hosts, port);
		}
		return instance;
	}
	
	/**
	 * Creates a connection if it is currently closed
	 */
	public void connect() {
		if(session == null && cluster == null) {
			this.connect(this.hosts, this.port);
		}
	}

	/**
	 * Closes the session & connection if they exist
	 */
	public void close() {
		if(session != null && cluster != null) {
			session.close();
			cluster.close();
			session = null;
			cluster = null;
		}
	}

	/**
	 * Returns a mapper of given entity type
	 * 
	 * @param type
	 *            the entity type to be used for the mapper
	 * 
	 * @return Cassandra mapper object
	 */
	public <T> Mapper<T> getMapper(Class<T> type) {
		return manager.mapper(type);
	}

	/**
	 * Executes a native Cassandra query, and returns the results as mapped Java
	 * objects of specified type
	 * 
	 * @param query
	 *            the native Cassandra query
	 * @param type
	 *            the entity type for the mapper
	 * 
	 * @return a list of specified object types
	 */
	public synchronized <T> Result<T> execute(String query, Class<T> type) {
		return getMapper(type).map(session.execute(query));
	}

}
