CREATE TABLE public."user"
(
    id integer NOT NULL,
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
        REFERENCES public.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    FOREIGN KEY (privilege_id)
        REFERENCES public.privilege (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.roles_privileges
    OWNER to postgres;

CREATE TABLE public.vehicle_category
(
    id smallint NOT NULL,
    name character varying NOT NULL,
    description character varying NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_category UNIQUE (name)
);

ALTER TABLE public.vehicle_category
    OWNER to postgres;

CREATE TABLE public.vehicle_model
(
    id smallint NOT NULL,
    name character varying NOT NULL,
    vehicle_category_id smallint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_model UNIQUE (name),
    FOREIGN KEY (vehicle_category_id)
        REFERENCES public.vehicle_category (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.vehicle_model
    OWNER to postgres;

CREATE TABLE public.vehicle
(
    id integer NOT NULL,
    daily_cost smallint NOT NULL,
    image bytea,
    description character varying,
    vehicle_model_id integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (vehicle_model_id)
        REFERENCES public.vehicle_model (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.vehicle
    OWNER to postgres;

CREATE TABLE public.rent_application
(
    id integer NOT NULL,
    "fromDate" timestamp without time zone NOT NULL,
    "toDate" timestamp without time zone NOT NULL,
    vehicle_id integer NOT NULL,
    user_id integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (vehicle_id)
        REFERENCES public.vehicle (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    FOREIGN KEY (user_id)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.rent_application
    OWNER to postgres;


ALTER TABLE public.vehicle
ALTER COLUMN daily_cost TYPE numeric;

ALTER TABLE public.vehicle_category
    ADD COLUMN image bytea;

ALTER TABLE public.vehicle DROP COLUMN image;

ALTER TABLE public.vehicle_model
    ADD COLUMN image bytea;

CREATE TABLE public.rent_application_status
(
    id smallint NOT NULL,
    name character varying NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_application_status UNIQUE (name)
);

ALTER TABLE public.rent_application_status
    OWNER to postgres;

ALTER TABLE public.rent_application
    ADD COLUMN rent_application_status_id smallint NOT NULL;

ALTER TABLE public.rent_application
    ADD FOREIGN KEY (rent_application_status_id)
        REFERENCES public.rent_application_status (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE public.rent_application
    RENAME "fromDate" TO from_date;

ALTER TABLE public.rent_application
    RENAME "toDate" TO to_date;

ALTER TABLE public.vehicle_model
ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY;

ALTER TABLE public.rent_application
ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY;

ALTER TABLE public.rent_application_status
ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY;

ALTER TABLE public.vehicle
ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY;

ALTER TABLE public.vehicle_category
ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY;

ALTER TABLE public.rent_application DROP COLUMN vehicle_id;

ALTER TABLE public.rent_application
    ADD COLUMN vehicle_model_id integer NOT NULL;

ALTER TABLE public.rent_application
    ADD FOREIGN KEY (vehicle_model_id)
        REFERENCES public.vehicle_model (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE public.rent_application
ALTER COLUMN from_date TYPE date;

ALTER TABLE public.rent_application
ALTER COLUMN to_date TYPE date;