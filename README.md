# Conference-management-system

### Database schema

#### Event

```postgres-psql
CREATE TABLE public.event
(
    id bigint NOT NULL DEFAULT nextval('event_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    program_id bigint,
    location_id bigint,
    CONSTRAINT event_pkey PRIMARY KEY (id),
    CONSTRAINT location_id FOREIGN KEY (location_id)
        REFERENCES public.location (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT program_id FOREIGN KEY (program_id)
        REFERENCES public.program (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.event
    OWNER to postgres;
-- Index: fki_location_id

-- DROP INDEX public.fki_location_id;

CREATE INDEX fki_location_id
    ON public.event USING btree
    (location_id ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: fki_program_id

-- DROP INDEX public.fki_program_id;

CREATE INDEX fki_program_id
    ON public.event USING btree
    (program_id ASC NULLS LAST)
    TABLESPACE pg_default;
```
#### Location
```postgres-psql
CREATE TABLE public.location
(
    id bigint NOT NULL DEFAULT nextval('location_id_seq'::regclass),
    country character varying COLLATE pg_catalog."default",
    city character varying COLLATE pg_catalog."default",
    CONSTRAINT location_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.location
    OWNER to postgres;
```

#### Program
```postgres-psql
CREATE TABLE public.program
(
    id bigint NOT NULL DEFAULT nextval('program_id_seq'::regclass),
    date timestamp without time zone,
    "interval" character varying COLLATE pg_catalog."default",
    abstract_deadline timestamp without time zone,
    proposal_deadline timestamp without time zone,
    bidding_deadline timestamp without time zone,
    CONSTRAINT program_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.program
    OWNER to postgres;
```
#### Proposal
```postgres-psql
CREATE TABLE public.paper
(
    id bigint NOT NULL DEFAULT nextval('proposal_id_seq'::regclass),
    title character varying COLLATE pg_catalog."default",
    content character varying COLLATE pg_catalog."default",
    qualifiers character varying[] COLLATE pg_catalog."default",
    topics text[] COLLATE pg_catalog."default",
    keywords text[] COLLATE pg_catalog."default",
    CONSTRAINT proposal_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.paper
    OWNER to postgres;
```

#### Recommendation
```postgres-psql
CREATE TABLE public.recommendation
(
    id bigint NOT NULL DEFAULT nextval('recommendation_id_seq'::regclass),
    text character varying COLLATE pg_catalog."default",
    email character varying COLLATE pg_catalog."default",
    proposal_id bigint,
    CONSTRAINT recommendation_pkey PRIMARY KEY (id),
    CONSTRAINT proposal_id FOREIGN KEY (proposal_id)
        REFERENCES public.paper (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.recommendation
    OWNER to postgres;
-- Index: fki_proposal_id

-- DROP INDEX public.fki_proposal_id;

CREATE INDEX fki_proposal_id
    ON public.recommendation USING btree
    (proposal_id ASC NULLS LAST)
    TABLESPACE pg_default;
```

#### Section
```postgres-psql
CREATE TABLE public.section
(
    id bigint NOT NULL DEFAULT nextval('section_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    event_id bigint,
    supervisor_email character varying COLLATE pg_catalog."default",
    CONSTRAINT section_pkey PRIMARY KEY (id),
    CONSTRAINT event_id FOREIGN KEY (event_id)
        REFERENCES public.event (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT supervisor_email FOREIGN KEY (supervisor_email)
        REFERENCES public.user_entity (email) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.section
    OWNER to postgres;
-- Index: fki_supervisor_email

-- DROP INDEX public.fki_supervisor_email;

CREATE INDEX fki_supervisor_email
    ON public.section USING btree
    (supervisor_email COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;
```

#### User_Entity
```postgres-psql
CREATE TABLE public.user_entity
(
    email character varying COLLATE pg_catalog."default" NOT NULL,
    section_id bigint,
    role character varying COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (email),
    CONSTRAINT section_id FOREIGN KEY (section_id)
        REFERENCES public.section (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.user_entity
    OWNER to postgres;
-- Index: fki_section_id

-- DROP INDEX public.fki_section_id;

CREATE INDEX fki_section_id
    ON public.user_entity USING btree
    (section_id ASC NULLS LAST)
    TABLESPACE pg_default;
```

#### User_Event
```postgres-psql
CREATE TABLE public.user_event
(
    email character varying COLLATE pg_catalog."default",
    event_id bigint,
    CONSTRAINT email FOREIGN KEY (email)
        REFERENCES public.user_entity (email) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT event_id FOREIGN KEY (event_id)
        REFERENCES public.event (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.user_event
    OWNER to postgres;
-- Index: fki_event_id

-- DROP INDEX public.fki_event_id;

CREATE INDEX fki_event_id
    ON public.user_event USING btree
    (event_id ASC NULLS LAST)
    TABLESPACE pg_default;
```

#### User_Proposal
```postgres-psql
CREATE TABLE public.user_proposal
(
    email character varying COLLATE pg_catalog."default",
    proposal_id bigint,
    CONSTRAINT email FOREIGN KEY (email)
        REFERENCES public.user_entity (email) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT proposal_id FOREIGN KEY (proposal_id)
        REFERENCES public.paper (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE public.user_proposal
    OWNER to postgres;
-- Index: fki_email

-- DROP INDEX public.fki_email;

CREATE INDEX fki_email
    ON public.user_proposal USING btree
    (email COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;
```