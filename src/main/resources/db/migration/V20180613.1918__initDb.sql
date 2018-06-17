CREATE TABLE public.account
(
  id SERIAL,
  created_at timestamp without time zone NOT NULL DEFAULT now(),
  username character varying(25),
  password character varying(550),
  passsword_salt character varying(550),
  role_id NUMERIC
);




create table public.role (
  id SERIAL,
  created_at timestamp without time zone NOT NULL DEFAULT now(),
  name character varying(25),
  code character varying(25)
);

create table public.account_session (
  token CHARACTER VARYING(255),
  account_id integer,
  valid_until TIMESTAMP without time zone not null default now(),

  CONSTRAINT user_session_token_key UNIQUE (token)
);



insert into public.role (name,code)  values ('Użytkownik', 'User');
insert into public.role (name,code)  values ('Administrator', 'Admin');