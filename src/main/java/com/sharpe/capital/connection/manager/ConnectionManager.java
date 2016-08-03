package com.sharpe.capital.connection.manager;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;

/**
 * Defines the contract for a Cassandra connection manager
 */
public interface ConnectionManager {

	/**
	 * Closes the session & connection
	 */
	void close();

	/**
	 * Returns a mapper of given entity type
	 * 
	 * @param type
	 *            the entity type to be used for the mapper
	 * 
	 * @return Cassandra mapper object
	 */
	<T> Mapper<T> getMapper(Class<T> type);

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
	<T> Result<T> execute(String query, Class<T> type);

}