INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`) VALUES (1,'p@s.com',true,'Pera','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','peki');
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`) VALUES (2,'l@s.com',true,'Laza','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','laki');
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`) VALUES (3,'d@s.com',true,'Djoka','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','djox');
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`) VALUES (4,'m@s.com',true,'Mika','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','miki');
INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `last_password_reset_date`, `password` ,`username`) VALUES (5,'t@s.com',true,'Tarzan','Peric','2017-10-01 21:58:58','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','tarzan');

INSERT INTO AUTHORITY (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO AUTHORITY (id, name) VALUES (2, 'ROLE_SYSTEM_ADMIN');
INSERT INTO AUTHORITY (id, name) VALUES (3, 'ROLE_AIRLINE_ADMIN');
INSERT INTO AUTHORITY (id, name) VALUES (4, 'ROLE_HOTEL_ADMIN');
INSERT INTO AUTHORITY (id, name) VALUES (5, 'ROLE_RAC_ADMIN');


INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 3);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (4, 4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (5, 5);

INSERT INTO `rentacar_company` (`id`,`name`,`description`) VALUES (1,'Pegaz','acab')
