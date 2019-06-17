INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`,`admin_password`) VALUES (2,'m@m.com',true,'Marko','Markovic','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','marko',false);
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`,`admin_password`) VALUES (3,'p@s.com',true,'Pera','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','peki',false);
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`,`admin_password`) VALUES (4,'l@s.com',true,'Laza','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','laki',false);
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`,`admin_password`) VALUES (5,'d@s.com',true,'Djoka','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','djox',false);
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`,`admin_password`) VALUES (6,'m@s.com',true,'Mika','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','miki',false);
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`,`admin_password`) VALUES (7,'t@s.com',true,'Tarzan','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','tarzan',false);
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`,`admin_password`) VALUES (8,'ms@s.com',true,'Mika','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','miki2',false);


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
INSERT INTO `user_authority` (user_id, authority_id) VALUES (8, 4);
INSERT INTO `user_authority` (user_id, authority_id) VALUES (9, 3);
INSERT INTO `user_authority` (user_id, authority_id) VALUES (10, 3);

INSERT INTO `friendship` VALUES (1, 0, 2);
INSERT INTO `friendship` VALUES (2, 0, 3);

INSERT INTO `users_friendships` VALUES (3, 1);
INSERT INTO `users_friendships` VALUES (2, 2);

-- Destinations
INSERT INTO `destination` VALUES (11, 'JFK', 'John. F. Kennedy. International Airport', 'New York', 'US', 123.23, 132.23);
INSERT INTO `destination` VALUES (12, 'BGD', 'Nikola Tesla International Airport', 'Belgrade', 'Serbia', 222.23, 543.23);
INSERT INTO `destination` VALUES (13, 'ATH', 'Athens Airport', 'Athens', 'Greece', 123.22, 423.22);

-- Airplanes
INSERT INTO `airplane` VALUES(20, 'Boeing 747', 10, 10);
INSERT INTO `airplane` VALUES(21, 'Boeing 567', 15, 15);
INSERT INTO `airplane` VALUES(22, 'Boeing 900', 8, 18);

-- Addresses
INSERT INTO `address` VALUES(31, 'New York', 'US', 1234, '1st street', 22);
INSERT INTO `address` VALUES(32, 'Belgrade', 'Serbia', 11000, 'Bulevar', 213);
INSERT INTO `address` VALUES(33, 'Athens', 'Greece', 5433, 'Alfa', 24);

-- Airlines
INSERT INTO `airline` VALUES(22, 'Best airline company in US', 'United', 4, 31, 5);
INSERT INTO `airline` VALUES(23, 'Najbolja aviokompanija', 'AirSerbia', 3, 32, 9);
INSERT INTO `airline` VALUES(24, 'Best company in Greece', 'AirGreece', 5, 33, 10);

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
INSERT INTO `flight_reservation` VALUES (71, true, false, 'Marko', 'Markovic', '12345', 1000, 0, '2019-01-01 00:00:00', 5, 6, 2);
INSERT INTO `flight_reservation` VALUES (72, true, false, 'Marko', 'Markovic', '12345', 1000, 0, '2019-01-04 01:00:00', 6, 7, 2);
INSERT INTO `flight_reservation` VALUES (73, true, false, 'Marko', 'Markovic', '12345', 1000, 0, '2019-02-01 00:00:00', 5, 7, 2);
INSERT INTO `flight_reservation` VALUES (74, true, false, 'Pera', 'Peric', '65432', 1400, 0, '2019-03-01 01:00:00', 6, 8, 3);
INSERT INTO `flight_reservation` VALUES (75, true, false, 'Pera', 'Peric', '65432', 1600, 0, '2019-03-01 00:00:00', 5, 9, 3);
INSERT INTO `flight_reservation` VALUES (76, true, false, 'Pera', 'Peric', '65432', 1500, 0, '2019-05-02 01:00:00', 1, 1, 3);

-- Users and flight reservations
INSERT INTO `users_reservations` VALUES (2, 71);
INSERT INTO `users_reservations` VALUES (2, 72);
INSERT INTO `users_reservations` VALUES (2, 73);
INSERT INTO `users_reservations` VALUES (3, 74);
INSERT INTO `users_reservations` VALUES (3, 75);
INSERT INTO `users_reservations` VALUES (3, 76);

-- Flight reservations
INSERT INTO `flight_reservations` VALUES (50, 71);
INSERT INTO `flight_reservations` VALUES (50, 72);
INSERT INTO `flight_reservations` VALUES (50, 73);
INSERT INTO `flight_reservations` VALUES (50, 74);
INSERT INTO `flight_reservations` VALUES (50, 75);
INSERT INTO `flight_reservations` VALUES (50, 76);


-- Hotels
INSERT INTO `hotel` (`id`, `name`, `description`, `rating`, `address_id`, `administrator_id`) VALUES (1, 'Hotel Plaza', 'Ovo je jedan jako dobar hotel sa prijatnim osobljem', 4.0, 32, 6)
INSERT INTO `hotel` (`id`, `name`, `description`, `rating`, `address_id`, `administrator_id`) VALUES (2, 'Hotel Continental', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 5.0, 33, 8)
INSERT INTO `hotel` (`id`, `name`, `description`, `rating`, `address_id`) VALUES (3, 'Hotel Bosna', 'Ovo je jedan jako dobar hotel sa prijatnim osobljem', 3.0, 32)

-- Rooms
INSERT INTO `room` (`id`, `room_number`, `floor`, `rating`, `capacity`, `hotel_id`) VALUES (1, 21, 2, 4.0, 3, 1);
INSERT INTO `room` (`id`, `room_number`, `floor`, `rating`, `capacity`, `hotel_id`) VALUES (2, 22, 2, 5.0, 2, 2);
INSERT INTO `room` (`id`, `room_number`, `floor`, `rating`, `capacity`, `hotel_id`) VALUES (3, 25, 2, 5.0, 2, 1);

INSERT INTO `hotel_rooms` (`hotel_id`, `rooms_id`) VALUES (1, 1)
INSERT INTO `hotel_rooms` (`hotel_id`, `rooms_id`) VALUES (2, 2)
INSERT INTO `hotel_rooms` (`hotel_id`, `rooms_id`) VALUES (1, 3)
-- Price List
INSERT INTO `hotel_price_list` (`id`, `description`, `hotel_id`) VALUES (1, 'Majski cjenovnik', 1);
INSERT INTO `hotel_price_list` (`id`, `description`, `hotel_id`) VALUES (2, 'Junski cjenovnik', 1);
INSERT INTO `hotel_price_list` (`id`, `description`, `hotel_id`) VALUES (3, 'Julski cjenovnik', 2);
INSERT INTO `hotel_price_list` (`id`, `description`, `hotel_id`) VALUES (4, 'Avgustovski cjenovnik', 3);


INSERT INTO `hotel_hotel_price_lists` (`hotel_id`, `hotel_price_lists_id`) VALUES (1,1)
INSERT INTO `hotel_hotel_price_lists` (`hotel_id`, `hotel_price_lists_id`) VALUES (1,2)
INSERT INTO `hotel_hotel_price_lists` (`hotel_id`, `hotel_price_lists_id`) VALUES (2,3)
INSERT INTO `hotel_hotel_price_lists` (`hotel_id`, `hotel_price_lists_id`) VALUES (3,4)

UPDATE `hotel` SET `current_price_list_id` = 1 WHERE `id` = 1;
UPDATE `hotel` SET `current_price_list_id` = 3 WHERE `id` = 2;
UPDATE `hotel` SET `current_price_list_id` = 4 WHERE `id` = 3;

-- Price List Item
INSERT INTO `hotel_price_list_item` (`id`, `active_discount`, `start_date`, `end_date`, `price_per_day`, `room_id`, `hotel_price_list_id`) VALUES (1, 0.5, '2019-10-01 22:00:00', '2019-10-01 22:30:00', 40, 1, 1);

-- list <-> list_item
INSERT INTO `hotel_price_list_hotel_price_list_items` (`hotel_price_list_id`, `hotel_price_list_items_id`) VALUES (1, 1);

-- room <-> list_item
INSERT INTO `room_hotel_price_list_items` (`room_id`, `hotel_price_list_items_id`) VALUES (1, 1)

INSERT INTO `rac` (`id`,`name`,`description`,rating) VALUES (1,'Pegaz','acab',1.0);
INSERT INTO `rac` (`id`,`name`,`description`,rating) VALUES (2,'Pegaz2','acab',1.5);

INSERT INTO `cars` (`id`,`name`,`brand`,`manuf_year`,`seats_number`,`rating`,`type`,`company_id`) VALUES (1,'stojdza','zastava',2059,5,5,0,1);
INSERT INTO `cars` (`id`,`name`,`brand`,`manuf_year`,`seats_number`,`rating`,`type`,`company_id`) VALUES (2,'jugic','zastava',2059,5,5,0,2);

INSERT INTO `cars` (`id`,`name`,`brand`,`manuf_year`,`seats_number`,`rating`,`type`,`company_id`) VALUES (3,'vozilo','mercedes',2059,2,5,3,1);
INSERT INTO `cars` (`id`,`name`,`brand`,`manuf_year`,`seats_number`,`rating`,`type`,`company_id`) VALUES (4,'kombi','mercedes',2059,10,5,2,2);

INSERT  INTO `racpricelistitem` (`id`,`active_discount`,`start_date`,`end_date`,`price_day`,`car_id`) VALUES (1,0,'2017-10-01 21:58:58','2017-10-10 21:58:58',100,1);
INSERT  INTO `racpricelistitem` (`id`,`active_discount`,`start_date`,`end_date`,`price_day`,`car_id`) VALUES (2,0.3,'2017-10-11 21:58:58','2017-10-21 21:58:58',50,1);
INSERT  INTO `racpricelistitem` (`id`,`active_discount`,`start_date`,`end_date`,`price_day`,`car_id`) VALUES (3,0.1,'2017-10-01 21:58:58','2017-10-10 21:58:58',100,2);
INSERT  INTO `racpricelistitem` (`id`,`active_discount`,`start_date`,`end_date`,`price_day`,`car_id`) VALUES (4,0.2,'2017-10-11 21:58:58','2017-10-21 21:58:58',50,2);


INSERT  INTO `racpricelistitem` (`id`,`active_discount`,`start_date`,`end_date`,`price_day`,`car_id`) VALUES (5,0,'2017-10-01 21:58:58','2017-10-10 21:58:58',100,3);
INSERT  INTO `racpricelistitem` (`id`,`active_discount`,`start_date`,`end_date`,`price_day`,`car_id`) VALUES (6,0,'2017-10-01 21:58:58','2017-10-10 21:58:58',100,4);

