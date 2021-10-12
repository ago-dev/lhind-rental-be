CREATE TABLE public."user"
(
    id bigint NOT NULL IDENTITY,
    first_name character varying(30) NOT NULL,
    last_name character varying(30) NOT NULL,
    username character varying(20) NOT NULL,
    email character varying(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_username UNIQUE (username),
    CONSTRAINT unique_email UNIQUE (email)
);

ALTER TABLE public."user"
    OWNER to postgres;

CREATE TABLE public.role
(
    id smallint NOT NULL,
    name character varying(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_name UNIQUE (name)
);

ALTER TABLE public.role
    OWNER to postgres;

ALTER TABLE public."user"
    ADD COLUMN role_id smallint NOT NULL;
ALTER TABLE public."user"
    ADD FOREIGN KEY (role_id)
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE public."user"
ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY;

ALTER TABLE public.role
ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY;

ALTER TABLE public."user"
    ADD COLUMN password character varying NOT NULL;

CREATE TABLE public.privilege
(
    id smallint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name character varying NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_privilege UNIQUE (name)
);

ALTER TABLE public.privilege
    OWNER to postgres;

CREATE TABLE public.roles_privileges
(
    role_id smallint NOT NULL,
    privilege_id smallint NOT NULL,
    PRIMARY KEY (role_id, privilege_id),
    FOREIGN KEY (role_id)
        REFERENCES None (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    FOREIGN KEY (privilege_id)
        REFERENCES None (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.roles_privileges
    OWNER to postgres;