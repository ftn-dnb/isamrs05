INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`) VALUES (2,'m@m.com',true,'Marko','Markovic','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','marko');
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`) VALUES (3,'p@s.com',true,'Pera','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','peki');
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`) VALUES (4,'l@s.com',true,'Laza','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','laki');
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`) VALUES (5,'d@s.com',true,'Djoka','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','djox');
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`) VALUES (6,'m@s.com',true,'Mika','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','miki');
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`) VALUES (7,'t@s.com',true,'Tarzan','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','tarzan');

INSERT INTO `authority` (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO `authority` (id, name) VALUES (2, 'ROLE_SYSTEM_ADMIN');
INSERT INTO `authority` (id, name) VALUES (3, 'ROLE_AIRLINE_ADMIN');
INSERT INTO `authority` (id, name) VALUES (4, 'ROLE_HOTEL_ADMIN');
INSERT INTO `authority` (id, name) VALUES (5, 'ROLE_RAC_ADMIN');

INSERT INTO `user_authority` (user_id, authority_id) VALUES (2, 1);
INSERT INTO `user_authority` (user_id, authority_id) VALUES (3, 1);
INSERT INTO `user_authority` (user_id, authority_id) VALUES (4, 2);
INSERT INTO `user_authority` (user_id, authority_id) VALUES (5, 3);
INSERT INTO `user_authority` (user_id, authority_id) VALUES (6, 4);
INSERT INTO `user_authority` (user_id, authority_id) VALUES (7, 5);

--INSERT INTO `friendship` VALUES (1, 0, 2);
--INSERT INTO `friendship` VALUES (2, 0, 3);

--INSERT INTO `users_friendships` VALUES (3, 1);
--INSERT INTO `users_friendships` VALUES (2, 2);

-- Destinations
INSERT INTO `destination` VALUES (11, 'JFK', 'John. F. Kennedy. International Airport', 'New York', 'US', 123.23, 132.23);
INSERT INTO `destination` VALUES (12, 'BGD', 'Nikola Tesla International Airport', 'Belgrade', 'Serbia', 222.23, 543.23);
INSERT INTO `destination` VALUES (13, 'ATH', 'Athens Airport', 'Athens', 'Greece', 123.22, 423.22);

-- Airplanes
INSERT INTO `airplane` VALUES(20, 'Boeing 747', 10, 20);
INSERT INTO `airplane` VALUES(21, 'Boeing 567', 15, 15);
INSERT INTO `airplane` VALUES(22, 'Boeing 900', 8, 18);

-- Addresses
INSERT INTO `address` VALUES(31, 'New York', 'US', 1234, '1st street', 22);
INSERT INTO `address` VALUES(32, 'Belgrade', 'Serbia', 11000, 'Bulevar', 213);
INSERT INTO `address` VALUES(33, 'Athens', 'Greece', 5433, 'Alfa', 24);

-- Airlines
INSERT INTO `airline` VALUES(22, 'Best airline company in US', 'United', 31);
INSERT INTO `airline` VALUES(23, 'Najbolja aviokompanija', 'AirSerbia', 32);
INSERT INTO `airline` VALUES(24, 'Best company in Greece', 'AirGreece', 33);

-- Destinations where airlines work
INSERT INTO `airline_destinations` VALUES(22, 11);
INSERT INTO `airline_destinations` VALUES(22, 12);
INSERT INTO `airline_destinations` VALUES(22, 13);
INSERT INTO `airline_destinations` VALUES(23, 11);
INSERT INTO `airline_destinations` VALUES(23, 12);
INSERT INTO `airline_destinations` VALUES(24, 13);

-- Flights
INSERT INTO `flight` VALUES(50, '2017-10-01 21:58:58', true, 5, '2017-09-30 21:58:58', 1200, 24, 22, 20, 11, 12);
INSERT INTO `flight` VALUES(51, '2016-10-01 21:58:58', false, 5, '2016-09-30 21:58:58', 1200, 24, 23, 21, 11, 12);

-- Airline price list items
INSERT INTO `airline_price_list_item` VALUES(60, 10, '2017-11-01 00:00:00', 1500, '2017-10-01 00:00:00');
INSERT INTO `airline_price_list_item` VALUES(61, 10, '2017-12-01 00:00:00', 1700, '2017-11-01 00:00:00');

-- Flight prices
INSERT INTO `flight_prices` VALUES (50, 60);
INSERT INTO `flight_prices` VALUES (50, 61);

-- Flight reservation
INSERT INTO `flight_reservation` VALUES (71, 1500, 0, '2017-12-01 00:00:00', 5, 6);
INSERT INTO `flight_reservation` VALUES (72, 1500, 0, '2017-12-01 01:00:00', 6, 7);

-- Flight reservations
INSERT INTO `flight_reservations` VALUES (50, 71);
INSERT INTO `flight_reservations` VALUES (50, 72);

INSERT INTO `rentacar_company` (`id`,`name`,`description`) VALUES (1,'Pegaz','acab')
