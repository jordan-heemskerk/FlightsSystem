CREATE TABLE Airline
  (
     code         CHAR(5) PRIMARY KEY,
     airline_name VARCHAR(255),
     website      VARCHAR(255)
  );

CREATE TABLE PlaneModel
  (
     code     CHAR(5) PRIMARY KEY,
     capacity INT
  );

CREATE TABLE Flight
  (
     num      CHAR(5) PRIMARY KEY,
     src      VARCHAR(255),
     destination VARCHAR(255)
  );

CREATE TABLE Operates
  (
     airlineCode    CHAR(5) REFERENCES Airline(code),
     planeModelCode CHAR(5) REFERENCES PlaneModel(code),
     flightNumber   CHAR(5) REFERENCES Flight(num)
  );

CREATE TABLE IncomingFlight
  (
     num   CHAR(5) PRIMARY KEY,
     plannedArrival DATE,
     CONSTRAINT fk_incomingflight
       FOREIGN KEY (num)
       REFERENCES Flight(num)
  );

CREATE TABLE OutgoingFlight
  (
     num     CHAR(5) PRIMARY KEY,
     plannedDeparture DATE,
     CONSTRAINT fk_outgoingflight
       FOREIGN KEY (num)
       REFERENCES Flight(num)
  );

CREATE TABLE Departures
  ( 
    departid INT PRIMARY KEY,
    gate VARCHAR(8), 
    departDate DATE 

  );

CREATE TABLE Arrivals ( 
    gate VARCHAR(8), 
    arriveDate DATE, 
    arriveid INT PRIMARY KEY
);

CREATE TABLE Outgoing
  (
     flightNumber CHAR(5) REFERENCES OutgoingFlight(num),
     departureID  INT REFERENCES Departures(departid)
  );

CREATE TABLE Incoming
  (
     flightNumber CHAR(5) REFERENCES IncomingFlight(num),
     arrivalID    INT REFERENCES Arrivals(arriveid)
  );

CREATE TABLE Passenger
  (
     passportNumber INT NOT NULL,
     passengername           VARCHAR(50),
     dateOfBirth    DATE,
     placeOfBirth   VARCHAR(50),
     citizenship    CHAR(3),
     PRIMARY KEY(passportNumber)
  );


CREATE TABLE FirstClass(
	passportNumber INT REFERENCES Passenger(passportNumber),
	cost DECIMAL(3,2),
	meal VARCHAR(20)
);

CREATE TABLE Economy(
	passportNumber INT REFERENCES Passenger(passportNumber),
	cost DECIMAL(3,2)
);

CREATE TABLE Infant(
	passportNumber INT REFERENCES Passenger(passportNumber),
	cost DECIMAL(3,2),
	age INT
);

CREATE TABLE SpecialNeeds(
	passportNumber INT REFERENCES Passenger(passportNumber),
	cost DECIMAL(3,2),
	equipment VARCHAR(20)
);

CREATE TABLE Senior(
	passportNumber INT REFERENCES Passenger(passportNumber),
	cost DECIMAL(3,2),
	age INT
);


CREATE TABLE DepartureStatus (
    info VARCHAR(255),
    id INT PRIMARY KEY
);

CREATE TABLE ArrivalStatus (
    info VARCHAR(255),
    id INT PRIMARY KEY
);

CREATE TABLE DepartureHasStatus
  (
     statusId    INT REFERENCES DepartureStatus(id),
     time DATE,
     departureId INT  REFERENCES Departures(departid)
  );

CREATE TABLE ArrivalHasStatus
  (
     statusId  INT REFERENCES ArrivalStatus(id),
     time DATE,
     arrivalId INT REFERENCES Arrivals(arriveid)
  );

CREATE TABLE onDepart
  (
     departureId INT REFERENCES Departures(departid),
     passenger   INT REFERENCES Passenger(passportnumber)
  );

CREATE TABLE onArrival
  (
     arrivalId INT REFERENCES Arrivals(arriveid),
     passenger INT REFERENCES Passenger(passportnumber)
  );


CREATE TABLE Baggage
  (
    flightNumber CHAR(5),
    passenger INT,
    weight INT,
    CONSTRAINT fk_baggage
      FOREIGN KEY (flightNumber)
      REFERENCES Flight(num),
      FOREIGN KEY (passenger)
      REFERENCES Passenger(passportNumber)
  );