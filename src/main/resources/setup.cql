create keyspace fx with replication = { 'class': 'SimpleStrategy', 'replication_factor': 1 };
use fx;
create table tick (symbol text, ask decimal, bid decimal, date timestamp, primary key(symbol, date)) with clustering order by (date desc);