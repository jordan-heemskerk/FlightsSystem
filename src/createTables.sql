CREATE TABLE Airline(code CHAR(5) PRIMARY KEY, 
			name VARCHAR(255), 
			website VARCHAR(255));

CREATE TABLE PlaneModel(code CHAR(5) PRIMARY KEY, 
			INT capacity);

CREATE TABLE Flight(number CHAR(5), 
			VARCHAR(255) source, 
			VARCHAR(255) destination);

CREATE TABLE Operates(airlineCode CHAR(5) FOREIGN KEY REFERENCES Airline(code),
			planeModelCode CHAR(5) FOREIGN KEY REFERENCES PlaneModel(code),
			flightNumber CHAR(5) FOREIGN KEY REFERENCES Flight(number));
			
CREATE TABLE IncomingFlight(flightNumber CHAR(5) FOREIGN KEY REFERENCES Flight(number),
			plannedArrival DATE);
				
CREATE TABLE OutgoingFlight(flightNumber CHAR(5) FOREIGN KEY REFERENCES Flight(number),
			plannedDeparture DATE);
			 
			