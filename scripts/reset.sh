#!/bin/bash
echo "drop user flightsystem cascade;" | sqlplus system/oracle@localhost:1521/orcl
