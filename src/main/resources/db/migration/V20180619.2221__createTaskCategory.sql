CREATE TABLE public.task
(
  id SERIAL,
  created_at timestamp without time zone NOT NULL DEFAULT now(),
  name character varying(25),
  account_id numeric,
  contents text,
  comment character varying(250)
);


CREATE TABLE public.category
(
  id SERIAL,
  created_at timestamp without time zone NOT NULL DEFAULT now(),
  name character varying(25),
  content text,
  comment character varying(250),
  is_done boolean,
  category_id numeric,
  account_id numeric
);
