# Data Access API
An abstraction API for accessing different data sources within the system architecture (RDBMS, NoSQL etc.)

The platform architecture currently uses two data sources:

1. Cassandra NoSQL cluster
2. Oracle 11g Master-Slave RDBMS
 
## Cassandra NoSQL

Cassandra is used to persist data sets in excess of 1m records, as it provides better scalability and performance with large data sets than traditional RDBMS. An example of data stored in Cassandra would be tick-by-tick exchange rates, which has the potential to grow to more than 1bn records in a short period of time (months, rather than years).

#### Data Model

_[INSERT DIAGRAM]_

#### Creation Steps

_[INSERT SCRIPT]_

## Oracle RDBMS

Oracle 11g is used as a relational database for smaller sets of reference data (to the order of 10s or 100s of thousands, rather than millions / billions). This offers more flexibility in the way we access the data compared to Cassandra, and we don't need to deal with the complexities of NoSQL for smaller datasets. It is also possible to build batch processes to load subsets of data from the Cassandra store into Oracle, for user interfaces or third-party applications.

#### Data Model

_[INSERT DIAGRAM]_

#### Creation Steps

_[INSERT SCRIPT]_

## Contributing

#### Tests
TBC

#### Documentation
Add documentation for every change. Feel free to send corrections or better docs! 

#### Pull Requests
Send _fixes_ PR on the `master` branch.

## License
MIT © [Sharpe Capital](http://sharpecapital.co.uk)
