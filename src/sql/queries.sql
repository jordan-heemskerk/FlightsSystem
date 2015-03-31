SELECT *
FROM Flight
WHERE num IN (SELECT flightNumber
			 FROM Operates
			 WHERE airlineCode IN (SELECT code
								  FROM Airline
								  WHERE airline_name = [input]));



SELECT *
FROM Flight
WHERE src = [input] OR destination = [input];



SELECT Departures.gate, Departures.departDate AS theDate, DepartureInfo.info
FROM Departures
JOIN (SELECT DepartureHasStatus.departureId AS departureId, DepartureStatus.info AS info
	  FROM DepartureStatus
	  JOIN DepartureHasStatus
	  ON DepartureStatus.id = DepartureHasStatus.statusId) DepartureInfo
ON Departures.departId = DepartureInfo.departureId
WHERE ABS(departDate - [inputDate]) < 0.15
UNION
SELECT Arrivals.gate, Arrivals.arriveDate AS theDate, ArrivalInfo.info
FROM Arrivals
JOIN (SELECT ArrivalHasStatus.arrivalId AS arrivalId, ArrivalStatus.info AS info
	  FROM ArrivalStatus
	  JOIN ArrivalHasStatus
	  ON ArrivalStatus.id = ArrivalHasStatus.statusId) ArrivalInfo
ON Arrivals.arriveId = ArrivalInfo.arrivalId
WHERE ABS(Arrivals.arriveDate - [inputDate]) < 0.15;



SELECT *
FROM Passenger
WHERE passportNumber IN (SELECT passenger
						 FROM onArrival
						 WHERE arrivalId = [input]
						 UNION
						 SELECT passenger
						 FROM onDepart
						 WHERE departureId = [input]);



SELECT *
FROM Baggage
WHERE passenger = [input1] AND flightNumber = [input2];



SELECT Incoming.FlightNumber, Outgoing.Flightnumber
FROM (SELECT DepartId, ArriveId
	  FROM Departures, Arrivals
	  WHERE Departures.DEPARTDATE - Arrivals.ArriveDate < 0.125) T1
LEFT JOIN Incoming ON T1.arriveId = Incoming.ARRIVALID
LEFT JOIN Outgoing ON T1.departId = Outgoing.DEPARTUREID;



SELECT *
FROM Passenger
WHERE passportNumber IN (SELECT onArrival.passenger
                        FROM onArrival
                        INNER JOIN onDepart
                        ON onArrival.passenger = onDepart.passenger
                        WHERE onArrival.arrivalId IN (SELECT arrivalId
                                            FROM ArrivalHasStatus
                                            WHERE time > SYSDATE)
                        AND onDepart.departureId IN (SELECT departureId
                                         FROM DepartureHasStatus
                                         WHERE time < SYSDATE));



SELECT *
FROM Passenger JOIN (
 SELECT Passenger, COUNT(*) as NumberOfFlights
 FROM OnArrival JOIN Passenger ON OnArrival.Passenger=Passenger.PassportNumber
 GROUP BY PassportNumber
 ORDER BY COUNT(*) DESC
) T ON Passenger.PassportNumber = T.Passenger
WHERE ROWNUM <= 3;



SELECT Flight.src, Flight.destination, DelayedFlights.airline_name, COUNT(DelayedFlights.airline_name) AS delays
FROM Flight
JOIN ( (SELECT Airline.airline_name AS airline_name, Operates.flightNumber AS flightNumber
		FROM Airline
		JOIN Operates
		ON Airline.code = Operates.airlineCode) AirlineFlights
	 JOIN  (SELECT Departure.departId, Departure.flightNumber
			FROM (SELECT Departures.departId AS departId, Outgoing.flightNumber AS flightNumber, Departures.departDate AS departDate
				  FROM Departures
				  JOIN Outgoing
				  ON Departures.departId = Outgoing.departureId) Departure
			JOIN DepartureHasStatus
			ON Departure.departId = DepartureHasStatus.departureId
			WHERE Departure.departDate < DepartureHasStatus.time) Delays
	 ON AirlineFlights.flightNumber = Delays.flightNumber) DelayedFlights
ON Flight.num = DelayedFlights.num
GROUP BY DelayedFlights.airline_name
ORDER BY delays DESC;