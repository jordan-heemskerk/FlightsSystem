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

//Insert Arrivals and Departures create table statements

CREATE TABLE Outgoing(flightNumber CHAR(5) FOREIGN KEY REFERENCES OutgoingFlight(number),
			departureID INT FOREIGN KEY REFERENCES Departures(id));
			
CREATE TABLE Incoming(flightNumber CHAR(5) FOREIGN KEY REFERENCES IncomingFlight(number),
			arrivalID INT FOREIGN KEY REFERENCES Arrivals(id));


CREATE TABLE Passenger(
	passportNumber INT NOT NULL,
	name VARCHAR(50),
	dateOfBirth DATE,
	placeOfBirth VARCHAR(50),
	citizenship CHAR(3),
	PRIMARY KEY(passportNumber)
);

CREATE TABLE FirstClass(
	passportNumber INT NOT NULL,
	cost DECIMAL(3,2),
	meal VARCHAR(20),
	FOREIGN KEY passportNumber REFERENCES Passenger(passportNumber)
);

CREATE TABLE Economy(
	passportNumber INT NOT NULL,
	cost DECIMAL(3,2),
	FOREIGN KEY passportNumber REFERENCES Passenger(passportNumber)
);

CREATE TABLE Infant(
	passportNumber INT NOT NULL,
	cost DECIMAL(3,2),
	age INT,
	FOREIGN KEY passportNumber REFERENCES Passenger(passportNumber)
);

CREATE TABLE SpecialNeeds(
	passportNumber INT NOT NULL,
	cost DECIMAL(3,2),
	equipment VARCHAR(20),
	FOREIGN KEY passportNumber REFERENCES Passenger(passportNumber)
);

CREATE TABLE Senior(
	passportNumber INT NOT NULL,
	cost DECIMAL(3,2),
	age INT,
	FOREIGN KEY passportNumber REFERENCES Passenger(passportNumber)
);

CREATE TABLE Departures (
    gate VARCHAR(8),
    departDate DATE,
    id INT AUTO_INCREMENT
);

CREATE TABLE Arrivals (
    gate VARCHAR(8),
    arriveDate DATE,
    id INT AUTO_INCREMENT
);


CREATE TABLE DepartureStatus (
    info VARCHAR(255),
    time DATE,
    id INT AUTO_INCREMENT
);

CREATE TABLE ArrivalStatus (
    info VARCHAR(255),
    time DATE,
    id INT AUTO_INCREMENT
);

CREATE TABLE DepartureHasStatus (
    statusId INT FOREIGN KEY REFERENCES DepartureStatus(id),
    departureId INT FOREIGN KEY REFERENCES Departures(id)
);

CREATE TABLE ArrivalHasStatus (
    statusId INT FOREIGN KEY REFERENCES DepartureStatus(id),
    arrivalId INT FOREIGN KEY REFERENCES Arrivals(id)
);

CREATE TABLE onDepart (
    departureId INT FOREIGN KEY REFERENCES Departures(id),
    passenger INT FOREIGN KEY REFERENCES Passenger(passportnum)
);

CREATE TABLE onArrival (
    arrivalId INT FOREIGN KEY REFERENCES Arrivals(id),
    passenger INT FOREIGN KEY REFERENCES Passenger(passportnum)
);

CREATE TABLE Baggage (
    flightNum CHAR(5) FOREIGN KEY REFERENCES Flight(number),
    weight INT,
    passenger INT FOREIGN KEY REFERENCES Passenger(passportnum),
    id INT AUTO_INCREMENT
);
