CREATE TABLE public.board
(
  id SERIAL,
  created_at timestamp without time zone NOT NULL DEFAULT now(),
  name character varying(25),
  account_id numeric
);