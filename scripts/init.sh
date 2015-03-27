#!/bin/bash
echo "FLIGHT SYSTEM INIT: Initializing database connection and tables"
sqlplus system/oracle@localhost:1521/orcl < src/sql/init.sql
sqlplus flightsystem/flightsystempass@localhost:1521/orcl < src/sql/createTables.sql
