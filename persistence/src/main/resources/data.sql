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