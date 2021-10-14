INSERT INTO public.role(id, name) VALUES (1, 'Member');
INSERT INTO public.role(id, name) VALUES (2, 'Admin');

INSERT INTO public."user" (first_name, last_name, username, email, role_id, password)
VALUES ('Devis', 'Ago', 'dago', 'agodevis98@gmail.com', 2, '$2a$12$G38clTrhpPdxXt3jJFIJkO7P8YdLU5TMMyEQcbn87WqUANcwUx4.i');

INSERT INTO public."user" (first_name, last_name, username, email, role_id, password)
VALUES ('Anonim', 'Anonim', 'anonim', 'dreamscape8991@gmail.com', 1, '$2a$12$G38clTrhpPdxXt3jJFIJkO7P8YdLU5TMMyEQcbn87WqUANcwUx4.i');

INSERT INTO public.privilege(id, name) VALUES (1, 'USER_READ');
INSERT INTO public.privilege(id, name) VALUES (2, 'USER_WRITE');
INSERT INTO public.privilege(id, name) VALUES (3, 'APPLICATION_READ');
INSERT INTO public.privilege(id, name) VALUES (4, 'APPLICATION_WRITE');

INSERT INTO public.roles_privileges(role_id, privilege_id) VALUES (1, 3);
INSERT INTO public.roles_privileges(role_id, privilege_id) VALUES (1, 4);
INSERT INTO public.roles_privileges(role_id, privilege_id) VALUES (2, 1);
INSERT INTO public.roles_privileges(role_id, privilege_id) VALUES (2, 2);
INSERT INTO public.roles_privileges(role_id, privilege_id) VALUES (2, 3);
INSERT INTO public.roles_privileges(role_id, privilege_id) VALUES (2, 4);

INSERT INTO public.vehicle_category(id, name, description)VALUES (1, 'Car', 'Reserve a new, clean car and a driver will bring it to you, whenever you need it.
');
INSERT INTO public.vehicle_category(id, name, description)VALUES (2, 'Van', 'Our minivans and passenger vans can seat up to 7 and 15 passengers respectively and are great for family vacations or accommodating large groups.');
INSERT INTO public.vehicle_category(id, name, description)VALUES (3, 'Sport utility vehicle',
'Full-size SUVs are great for weekend road trips and special occasions. Start a reservation to see pricing and availability for daily and weekly rentals.');

INSERT INTO public.vehicle_model(id, name, vehicle_category_id)VALUES (1, 'Mercedes-Benz C-Class', 1);
INSERT INTO public.vehicle_model(id, name, vehicle_category_id)VALUES (2, 'Audi A6', 1);

INSERT INTO public.rent_application_status(id, name)VALUES (1, 'Pending');
INSERT INTO public.rent_application_status(id, name)VALUES (2, 'Approved');
INSERT INTO public.rent_application_status(id, name)VALUES (3, 'Declined');
