--
-- Airport
--

CREATE TABLE public.airport
(
    id           bigint                 NOT NULL,
    code         character varying(5)   NOT NULL,
    name         character varying(100) NOT NULL,
    updated_date timestamp without time zone,
    created_date timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT airport_pkey PRIMARY KEY (id),
    CONSTRAINT airport_ak UNIQUE (code)
);

CREATE SEQUENCE public.airport_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

--
-- Aircraft
--

CREATE TABLE public.aircraft
(
    id           bigint                NOT NULL,
    name         character varying(50) NOT NULL,
    type         smallint              NOT NULL,
    airport_id   bigint                NOT NULL,
    updated_date timestamp without time zone,
    created_date timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT aircraft_pkey PRIMARY KEY (id),
    CONSTRAINT aircraft_airport_fk FOREIGN KEY (airport_id) REFERENCES public.airport (id)
);

CREATE SEQUENCE public.aircraft_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;
