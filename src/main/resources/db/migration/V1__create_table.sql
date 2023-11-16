CREATE TABLE public.aircraft
(
    id           bigint                NOT NULL,
    name         character varying(50) NOT NULL,
    updated_date timestamp without time zone,
    created_date timestamp without time zone DEFAULT now() NOT NULL
);

CREATE SEQUENCE public.aircraft_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;
